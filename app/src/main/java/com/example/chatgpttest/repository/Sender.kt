package com.example.chatgpttest.repository

import com.example.chatgpttest.model.ChatGptPayload
import com.example.chatgpttest.model.ChatGptResponse
import com.example.chatgpttest.model.Choice
import com.google.gson.Gson
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

private const val apiKey = "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"
private const val apiUrl = "https://api.openai.com/v1/completions"

fun sendRequest2ToChatGPT(text: String): MutableList<Choice> {
    val httpClient = OkHttpClient()
    val gson = Gson()


    val headers = mapOf(
        "Content-Type" to "application/json",
        "Authorization" to "Bearer $apiKey"
    )

    val requestBody = ChatGptPayload(
        prompt = text,
        max_tokens = 4000,
        n = 1,
        temperature = 1.0
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
            val gptResponse = gson.fromJson(responseBody, ChatGptResponse::class.java)
            mutableListOf(Choice(gptResponse.choices[0].text))
        } else {
            mutableListOf(Choice("Error: ${response.code}"))
        }
    } catch (e: IOException) {
        mutableListOf(Choice("Error: ${e.printStackTrace()}"))
    }
}
