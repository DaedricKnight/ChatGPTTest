package com.example.chatgpttest.data.api

import com.example.chatgpttest.data.constants.CompletionsEndpoint
import com.example.chatgpttest.domain.model.ChatGPTResponse
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ChatGPTApi {
    @POST(CompletionsEndpoint)
    suspend fun getGeneratedText(
        @Body body: JsonObject
    ): Response<ChatGPTResponse>

    @POST(CompletionsEndpoint)
    @Streaming
    fun getFlowText(
        @Body body: JsonObject
    ): Call<ResponseBody>
}