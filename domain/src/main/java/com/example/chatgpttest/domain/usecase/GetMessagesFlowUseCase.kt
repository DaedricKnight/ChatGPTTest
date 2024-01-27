package com.example.chatgpttest.domain.usecase

import com.example.chatgpttest.domain.repository.ChatGPTRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesFlowUseCase @Inject constructor(
    private val chatGPTRepository: ChatGPTRepository
) {
    suspend fun getMessageFlow(inputText: String, maxTokens: Int, n: Int): Flow<String> {
        return chatGPTRepository.getMessagesFlow(inputText, maxTokens, n)
    }
}