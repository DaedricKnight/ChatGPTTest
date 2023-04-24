package com.example.chatgpttest.data.viewmodel

import com.example.chatgpttest.data.repository.ChatGPTRepository
import com.example.chatgpttest.model.Choice
import com.example.chatgpttest.viewmodel.ChatGPTViewModel
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
        //TODO add viewmodel.updateChat() tests
    }

    @Test
    fun testResetChat() = runTest {
        viewModel.updateChat(Choice(text = "How are you?"))
        viewModel.resetChat()
        Assert.assertEquals(viewModel.conversationsState.value.size, 0)
    }

}