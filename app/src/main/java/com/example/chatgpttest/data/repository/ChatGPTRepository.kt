package com.example.chatgpttest.data.repository

import com.example.chatgpttest.model.ChatGPTResponse
import kotlinx.coroutines.flow.Flow

interface ChatGPTRepository {
    suspend fun getCompletionResponse(inputText: String, maxTokens: Int, n: Int): ChatGPTResponse
    suspend fun getMessagesFlow(inputText: String, maxTokens: Int, n: Int): Flow<String>
}