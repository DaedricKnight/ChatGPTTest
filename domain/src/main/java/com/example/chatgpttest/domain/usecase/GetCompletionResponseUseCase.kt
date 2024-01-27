package com.example.chatgpttest.domain.usecase

import com.example.chatgpttest.domain.model.ChatGPTResponse
import com.example.chatgpttest.domain.repository.ChatGPTRepository
import javax.inject.Inject

class GetCompletionResponseUseCase @Inject constructor(
    private val chatGPTRepository: ChatGPTRepository
) {
    suspend fun getCompletionResponse(inputText: String, maxTokens: Int, n: Int): ChatGPTResponse {
        return chatGPTRepository.getCompletionResponse(inputText, maxTokens, n)
    }
}