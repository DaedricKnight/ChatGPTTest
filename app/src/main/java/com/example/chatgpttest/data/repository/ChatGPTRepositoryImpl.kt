package com.example.chatgpttest.data.repository

import com.example.chatgpttest.constants.emptyResponse
import com.example.chatgpttest.constants.errorId
import com.example.chatgpttest.constants.errorObject
import com.example.chatgpttest.constants.temperature
import com.example.chatgpttest.data.api.ChatGPTApi
import com.example.chatgpttest.model.*
import javax.inject.Inject

class ChatGPTRepositoryImpl @Inject constructor(private val chatGPTApi: ChatGPTApi) :
    ChatGPTRepository {
    override suspend fun getCompletionResponse(inputText: String, maxTokens: Int, n: Int): ChatGPTResponse {

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
                        ChatGPTChoice(
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
                    ChatGPTChoice(
                        text = "Error Code = ${response.code()}",
                        index = 1,
                        isChatGPT = true
                    )
                ),
                objectType = errorObject
            )
        }
    }
}