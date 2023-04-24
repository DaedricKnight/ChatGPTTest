package com.example.chatgpttest.data.repository

import com.example.chatgpttest.model.*
import com.google.gson.Gson
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

private const val apiKey = "YOUR-API-KEY"
private const val apiUrl = "https://api.openai.com/v1/completions"

suspend fun sendRequest2ToChatGPT(text: String): MutableList<Choice> {
    val httpClient = OkHttpClient()
    val gson = Gson()


    val headers = mapOf(
        "Content-Type" to "application/json",
        "Authorization" to "Bearer $apiKey"
    )

    val requestBody = ChatGPTPayload(
        prompt = text,
        maxTokens = 4000,
        n = 1,
        temperature = 1.0,
        messages = listOf(
            ChatGPTMessage(
                role = Role.user,
                content = text
            )
        )
    )
    val json = gson.toJson(requestBody)

    val request = Request.Builder()
        .url(apiUrl)
        .headers(headers.toHeaders())
        .post(json.toRequestBody("application/json".toMediaType()))
        .build()

    return try {
        val response = httpClient.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val gptResponse = gson.fromJson(responseBody, ChatGPTResponse::class.java)
            mutableListOf(Choice(gptResponse.choices[0].text))
        } else {
            mutableListOf(Choice("Error: ${response.code}"))
        }
    } catch (e: IOException) {
        mutableListOf(Choice("Error: ${e.printStackTrace()}"))
    }
}
