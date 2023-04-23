package com.example.chatgpttest.data

import com.example.chatgpttest.constants.CompletionsEndpoint
import com.example.chatgpttest.model.ChatGPTResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGPTApi {
    @POST(CompletionsEndpoint)
    suspend fun getGeneratedText(
        @Body body: JsonObject
    ): Response<ChatGPTResponse>
}