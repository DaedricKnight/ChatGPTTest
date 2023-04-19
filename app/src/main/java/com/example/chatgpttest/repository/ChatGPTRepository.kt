package com.example.chatgpttest.repository

import com.example.chatgpttest.data.ChatGPTApi
import com.example.chatgpttest.model.ChatGPTResponse
import com.example.chatgpttest.model.ChatGptPayload
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

private const val apiKey = "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"

class ChatGPTRepository @Inject constructor(private val chatGPTApi: ChatGPTApi) {
    suspend fun getResponse(inputText: String): ChatGPTResponse? {

        val requestBody = ChatGptPayload(
            prompt = inputText,
            max_tokens = 4000,
            n = 1,
            temperature = 50.0
        )

        val json = Gson().toJson(requestBody)
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val request = json.toRequestBody(jsonMediaType)

        val response = chatGPTApi.getGeneratedText(apiKey, request)

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}