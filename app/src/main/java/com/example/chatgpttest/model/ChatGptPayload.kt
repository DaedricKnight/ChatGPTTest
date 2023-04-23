package com.example.chatgpttest.model

import com.google.gson.JsonObject

data class ChatGptPayload(
    val model: String = "text-davinci-003",
    val prompt: String,
    val maxTokens: Int,
    val n: Int = 1,
    val stream: Boolean = false,
    val temperature: Double = 1.0,
    val top_p: Int = 1,
    val stop: String? = null,
    val messages: List<ChatGptMessage>
)

data class ChatGptMessage(
    val role: String,
    val content: String
)

fun ChatGptPayload.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("temperature", temperature)
    json.addProperty("stream", stream)
    json.addProperty("model", model)
    json.addProperty("prompt", prompt)
    return json
}