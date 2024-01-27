package com.example.chatgpttest.data.model

import com.example.chatgpttest.domain.model.ChatGPTChoice
import com.example.chatgpttest.domain.model.ChatGPTResponse
import com.example.chatgpttest.domain.model.ChatGPTUsage

class FakeResponse {
    val chatResponse = ChatGPTResponse(
        id = "id",
        objectType = "skynet object",
        created = 1682308285,
        model = "text-davinci-003",
        usage = ChatGPTUsage(
            prompt_tokens = 100,
            completion_tokens = 100,
            total_tokens = 200
        ),
        choices = listOf(
            ChatGPTChoice(
                text = "The Universe is too old for this flutter shit.",
                index = 0,
                logprobs = null,
                finish_reason = "reason"
            )
        )
    )

    val emptyResponse = ChatGPTResponse(
        id = "",
        objectType = "",
        created = 0,
        model = "",
        usage = ChatGPTUsage(
            prompt_tokens = 0,
            completion_tokens = 0,
            total_tokens = 0
        ),
        choices = listOf(
            ChatGPTChoice(
                text = "",
                index = 0,
                logprobs = null,
                finish_reason = ""
            )
        )
    )
}