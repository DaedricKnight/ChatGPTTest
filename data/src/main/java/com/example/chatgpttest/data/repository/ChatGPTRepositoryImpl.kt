package com.example.chatgpttest.data.repository

import com.example.chatgpttest.data.api.ChatGPTApi
import com.example.chatgpttest.data.constants.choicesDes
import com.example.chatgpttest.data.constants.dataDone
import com.example.chatgpttest.data.constants.dataPrefix
import com.example.chatgpttest.data.constants.emptyResponse
import com.example.chatgpttest.data.constants.errorId
import com.example.chatgpttest.data.constants.errorObject
import com.example.chatgpttest.data.constants.temperature
import com.example.chatgpttest.data.constants.textDes
import com.example.chatgpttest.data.constants.unableToParseText
import com.example.chatgpttest.domain.repository.ChatGPTRepository
import com.example.chatgpttest.domain.model.ChatGPTMessage
import com.example.chatgpttest.domain.model.ChatGPTPayload
import com.example.chatgpttest.domain.model.ChatGPTResponse
import com.example.chatgpttest.domain.model.Role
import com.example.chatgpttest.domain.model.toJson
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class ChatGPTRepositoryImpl @Inject constructor(private val chatGPTApi: ChatGPTApi) :
    ChatGPTRepository {
    override suspend fun getCompletionResponse(
        inputText: String,
        maxTokens: Int,
        n: Int
    ): ChatGPTResponse {

        val requestBody = ChatGPTPayload(
            prompt = inputText,
            maxTokens = maxTokens,
            n = n,
            temperature = temperature,
            messages = listOf(
                ChatGPTMessage(
                    role = Role.user,
                    content = inputText
                )
            )
        )

        val response = chatGPTApi.getGeneratedText(requestBody.toJson())

        return if (response.isSuccessful) {
            if (response.body() != null) {
                response.body()!!
            } else {
                ChatGPTResponse(
                    id = errorId,
                    choices = listOf(
                        com.example.chatgpttest.domain.model.ChatGPTChoice(
                            text = emptyResponse,
                            index = 1,
                            isChatGPT = true
                        )
                    ),
                    objectType = errorObject
                )
            }
        } else {
            ChatGPTResponse(
                id = errorId,
                choices = listOf(
                    com.example.chatgpttest.domain.model.ChatGPTChoice(
                        text = "Error Code = ${response.code()}, message = ${response.message()}",
                        index = 1,
                        isChatGPT = true
                    )
                ),
                objectType = errorObject
            )
        }
    }

    override suspend fun getMessagesFlow(inputText: String, maxTokens: Int, n: Int): Flow<String> =
        flow {
            val requestBody = ChatGPTPayload(
                prompt = inputText,
                maxTokens = maxTokens,
                n = n,
                stream = true,
                temperature = temperature,
                messages = listOf(
                    ChatGPTMessage(
                        role = Role.user,
                        content = inputText
                    )
                )
            )
            val response = chatGPTApi.getFlowText(requestBody.toJson()).execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.byteStream()?.bufferedReader()?.use { input ->
                    generateSequence { input.readLine() }.forEachIndexed { index, line ->
                        if (line == dataDone) {
                            return@use
                        } else if (line.startsWith(dataPrefix)) {
                            try {
                                val value = lookupDataFromResponse(line)
                                if (value.isNotEmpty() && index < 3) {
                                    emit(value.replace("\n", ""))
                                } else {
                                    emit(value)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                val errorMessage = try {
                    JSONObject(errorBody).toString()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    unableToParseText
                }
                emit("Failure! Try again. Error: $errorMessage")
            }
        }.flowOn(Dispatchers.IO)

    private fun lookupDataFromResponse(jsonString: String): String {
        val dataPrefix = dataPrefix
        if (!jsonString.startsWith(dataPrefix)) {
            return ""
        }

        val json = jsonString.substring(dataPrefix.length).trim()
        return try {
            val gson = Gson()
            val jsonObject = gson.fromJson(json, JsonObject::class.java)
            val choices = jsonObject.getAsJsonArray(choicesDes)
            if (choices.size() > 0) {
                val choice = choices.get(0).asJsonObject
                choice.get(textDes).asString
            } else {
                ""
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            ""
        }
    }
}