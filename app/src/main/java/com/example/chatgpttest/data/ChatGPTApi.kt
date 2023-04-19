package com.example.chatgpttest.data

import com.example.chatgpttest.model.ChatGPTResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatGPTApi {
    @POST("v1/completions")
    fun getGeneratedText(
        @Header("Authorization") apiKey: String,
        @Body requestBody: RequestBody
    ): Response<ChatGPTResponse>
}