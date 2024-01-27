package com.example.chatgpttest.data.viewmodel

import com.example.chatgpttest.domain.model.ChatGPTChoice
import com.example.chatgpttest.domain.model.ChatGPTResponse
import com.example.chatgpttest.domain.repository.ChatGPTRepository
import com.example.chatgpttest.domain.usecase.GetCompletionResponseUseCase
import com.example.chatgpttest.domain.usecase.GetMessagesFlowUseCase
import com.example.chatgpttest.viewmodel.ChatGPTViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

    private val getCompletionResponseUseCase = mockk<GetCompletionResponseUseCase>(relaxed = true)
    private val getMessagesFlowUseCase = mockk<GetMessagesFlowUseCase>(relaxed = true)
    private lateinit var viewModel: ChatGPTViewModel

    @Before
    fun setup() {
        viewModel = ChatGPTViewModel(getCompletionResponseUseCase, getMessagesFlowUseCase)
    }

    @Test
    fun testAddConversation() = runTest {
        coEvery {
            getCompletionResponseUseCase.getCompletionResponse(
                any(),
                any(),
                any()
            )
        } returns ChatGPTResponse(
            id = "success_id",
            choices = listOf(
                ChatGPTChoice(
                    text = "I'm good, thanks.",
                    finish_reason = "",
                    index = 0,
                    logprobs = null
                )
            ),
            objectType = "success_object"
        )
        viewModel.updateChat(
            ChatGPTChoice(
                text = "How are you?",
                index = 0
            )
        )
        advanceUntilIdle()
        val result = viewModel.conversationsState.value
        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0].text, "How are you?")
        Assert.assertEquals(result[1].text, "I'm good, thanks.")
    }

    @Test
    fun testEmptyConversation() = runTest {
        coEvery {
            getCompletionResponseUseCase.getCompletionResponse(
                any(),
                any(),
                any()
            )
        } returns ChatGPTResponse(
            id = "empty_id",
            choices = listOf(),
            objectType = "empty_object"
        )
        viewModel.updateChat(
            ChatGPTChoice(
                text = "How are you?",
                index = 0
            )
        )
        advanceUntilIdle()
        val result = viewModel.conversationsState.value
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0].text, "How are you?")
    }

    @Test
    fun testResetChat() = runTest {
        viewModel.updateChat(
            ChatGPTChoice(
                text = "How are you?",
                index = 0
            )
        )
        viewModel.resetChat()
        Assert.assertEquals(viewModel.conversationsState.value.size, 0)
    }

    @Test
    fun testEmptyFlow() = runTest {
        coEvery {
            getMessagesFlowUseCase.getMessageFlow(
                any(),
                any(),
                any()
            )
        } returns flowOf()
        viewModel.updateChatStream(
            ChatGPTChoice(
                text = "How are you?",
                index = 0
            )
        )
        advanceUntilIdle()
        val result = viewModel.conversationsState.value
        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0].text, "How are you?")
        Assert.assertEquals(result[1].text, "Typing...")
    }

    @Test
    fun testEmptyStringFlow() = runTest {
        coEvery {
            getMessagesFlowUseCase.getMessageFlow(
                any(),
                any(),
                any()
            )
        } returns flowOf("")
        viewModel.updateChatStream(
            ChatGPTChoice(
                text = "How are you?",
                index = 0
            )
        )
        advanceUntilIdle()
        val result = viewModel.conversationsState.value
        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0].text, "How are you?")
        Assert.assertEquals(result[1].text, "")
    }

    @Test
    fun testAddFlow() = runTest {
        coEvery {
            getMessagesFlowUseCase.getMessageFlow(
                any(),
                any(),
                any()
            )
        } returns flowOf("I'm good, thanks.", " And you?")
        viewModel.updateChatStream(
            ChatGPTChoice(
                text = "How are you?",
                index = 0
            )
        )
        advanceUntilIdle()
        val result = viewModel.conversationsState.value
        Assert.assertEquals(result.size, 2)
        Assert.assertEquals(result[0].text, "How are you?")
        Assert.assertEquals(result[1].text, "I'm good, thanks. And you?")
    }

}