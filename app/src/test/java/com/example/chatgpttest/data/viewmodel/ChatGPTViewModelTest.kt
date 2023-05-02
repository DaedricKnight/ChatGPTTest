package com.example.chatgpttest.data.viewmodel

import com.example.chatgpttest.data.repository.ChatGPTRepository
import com.example.chatgpttest.model.ChatGPTChoice
import com.example.chatgpttest.model.ChatGPTResponse
import com.example.chatgpttest.viewmodel.ChatGPTViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatGPTViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    private val repository = mockk<ChatGPTRepository>(relaxed = true)
    private lateinit var viewModel: ChatGPTViewModel

    @Before
    fun setup() {
        viewModel = ChatGPTViewModel(repository)
    }

    @Test
    fun testAddConversation() = runTest {
        coEvery { repository.getCompletionResponse(any()) } returns ChatGPTResponse(
            id = "error_id",
            choices = listOf(
                ChatGPTChoice(
                    text = "I'm good, thanks.",
                    finish_reason = "",
                    index = 0,
                    logprobs = null
                )
            ),
            objectType = "error_object"
        )
        viewModel.updateChat(ChatGPTChoice(text = "How are you?", index = 0))
        advanceUntilIdle()
        val result = viewModel.conversationsState.value
        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0].text, "How are you?")
        Assert.assertEquals(result[1].text, "I'm good, thanks.")
    }

    @Test
    fun testResetChat() = runTest {
        viewModel.updateChat(ChatGPTChoice(text = "How are you?", index = 0))
        viewModel.resetChat()
        Assert.assertEquals(viewModel.conversationsState.value.size, 0)
    }

}