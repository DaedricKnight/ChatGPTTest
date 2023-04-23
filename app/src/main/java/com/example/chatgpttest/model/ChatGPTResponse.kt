package com.example.chatgpttest.model

import com.google.gson.annotations.SerializedName

data class ChatGPTResponse(
    val id: String,
    @SerializedName("object") val objectType: String,
    val created: Long,
    val model: String,
    val usage: ChatGPTUsage,
    val choices: List<ChatGPTChoice>
)

data class ChatGPTUsage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class ChatGPTChoice(
    val text: String,
    val index: Int,
    val logprobs: Any?,
    val finish_reason: String
)
