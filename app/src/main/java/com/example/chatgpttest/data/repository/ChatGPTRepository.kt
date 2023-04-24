package com.example.chatgpttest.data.repository

interface ChatGPTRepository {
    suspend fun getCompletionResponse(inputText: String): String
}