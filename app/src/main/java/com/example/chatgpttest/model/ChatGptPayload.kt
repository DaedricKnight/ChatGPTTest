package com.example.chatgpttest.model

data class ChatGptPayload(
    val model: String = "text-davinci-003",
    val prompt: String,
    val max_tokens: Int,
    val n: Int = 1,
    val temperature: Double,
    val top_p: Int = 1,
    val frequency_penalty: Int = 0,
    val presence_penalty: Int = 0,
    val stop: String? = null,
)
