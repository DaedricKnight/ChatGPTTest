package com.example.chatgpttest.data

import com.example.chatgpttest.model.ChatGptPayload
import com.example.chatgpttest.model.ChatGptResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatGptApi {
    @POST("v1/completions")
    fun getGeneratedText(
        @Header("Authorization") apiKey: String,
        @Body payload: ChatGptPayload
    ): Call<ChatGptResponse>
}