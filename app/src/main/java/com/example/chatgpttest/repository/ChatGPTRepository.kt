package com.example.chatgpttest.repository

import com.example.chatgpttest.data.ChatGPTApi
import com.example.chatgpttest.model.ChatGptMessage
import com.example.chatgpttest.model.ChatGptPayload
import com.example.chatgpttest.model.toJson
import javax.inject.Inject

private const val ROLE = "user"

class ChatGPTRepository @Inject constructor(private val chatGPTApi: ChatGPTApi) {
    suspend fun getCompletionResponse(inputText: String): String {

        val requestBody = ChatGptPayload(
            prompt = inputText,
            maxTokens = 4000,
            n = 1,
            temperature = 1.0,
            messages = listOf(
                ChatGptMessage(
                    role = ROLE,
                    content = inputText
                )
            )
        )

        val response = chatGPTApi.getGeneratedText(requestBody.toJson())

        return if (response.isSuccessful) {
            response.body()?.choices?.get(0)?.text ?:"Empty response"
        } else {
            response.code().toString()
        }
    }
}