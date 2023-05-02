package com.example.chatgpttest.model

import com.google.gson.JsonObject

data class ChatGPTPayload(
    val model: String = "text-davinci-003",
    val prompt: String,
    val maxTokens: Int,
    val n: Int = 10,
    val stream: Boolean = false,
    val temperature: Double = 2.0,
    val top_p: Int = 1,
    val stop: String? = null,
    val messages: List<ChatGPTMessage>
)

data class ChatGPTMessage(
    val role: Role,
    val content: String
)

fun ChatGPTPayload.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("temperature", temperature)
    json.addProperty("stream", stream)
    json.addProperty("model", model)
    json.addProperty("prompt", prompt)
    json.addProperty("n", n)
    json.addProperty("max_tokens", maxTokens)
    return json
}