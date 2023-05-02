package com.example.chatgpttest.data.repository

import com.example.chatgpttest.model.ChatGPTResponse

interface ChatGPTRepository {
    suspend fun getCompletionResponse(inputText: String, maxTokens: Int, n: Int): ChatGPTResponse
}