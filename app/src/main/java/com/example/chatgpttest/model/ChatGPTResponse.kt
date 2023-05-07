package com.example.chatgpttest.model

import com.google.gson.annotations.SerializedName

data class ChatGPTResponse(
    val id: String,
    @SerializedName("object") val objectType: String,
    val created: Long = 0,
    val model: String = "text-davinci-003",
    val usage: ChatGPTUsage? = null,
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
    val logprobs: Any? = null,
    val finish_reason: String = "end of tokens",
    val isChatGPT: Boolean = false
)
