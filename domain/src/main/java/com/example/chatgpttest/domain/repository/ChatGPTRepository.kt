package com.example.chatgpttest.domain.repository

import com.example.chatgpttest.domain.model.ChatGPTResponse
import kotlinx.coroutines.flow.Flow

interface ChatGPTRepository {
    suspend fun getCompletionResponse(inputText: String, maxTokens: Int, n: Int): ChatGPTResponse
    suspend fun getMessagesFlow(inputText: String, maxTokens: Int, n: Int): Flow<String>
}