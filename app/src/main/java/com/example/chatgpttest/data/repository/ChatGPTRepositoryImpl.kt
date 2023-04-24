package com.example.chatgpttest.data.repository

import com.example.chatgpttest.data.api.ChatGPTApi
import com.example.chatgpttest.model.ChatGPTMessage
import com.example.chatgpttest.model.ChatGPTPayload
import com.example.chatgpttest.model.Role
import com.example.chatgpttest.model.toJson
import javax.inject.Inject

class ChatGPTRepositoryImpl @Inject constructor(private val chatGPTApi: ChatGPTApi) :
    ChatGPTRepository {
    override suspend fun getCompletionResponse(inputText: String): String {

        val requestBody = ChatGPTPayload(
            prompt = inputText,
            maxTokens = 4000,
            n = 1,
            temperature = 1.0,
            messages = listOf(
                ChatGPTMessage(
                    role = Role.user,
                    content = inputText
                )
            )
        )

        val response = chatGPTApi.getGeneratedText(requestBody.toJson())

        return if (response.isSuccessful) {
            response.body()?.choices?.get(0)?.text ?: "Empty response"
        } else {
            "Error Code = ${response.code()}"
        }
    }
}