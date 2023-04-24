package com.example.chatgpttest.data.repository

import com.example.chatgpttest.data.api.ChatGPTApi
import com.example.chatgpttest.data.model.FakeResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ChatGPTRepositoryTest {
    private val api = mockk<ChatGPTApi>(relaxed = true)
    private lateinit var repository: ChatGPTRepository

    @Before
    fun setup() {
        repository = ChatGPTRepositoryImpl(api)
    }

    @Test
    fun successTest() = runBlocking {
        val response = FakeResponse().chatResponse
        coEvery { api.getGeneratedText(any()).isSuccessful } returns true
        coEvery { api.getGeneratedText(any()).body() } returns response
        val result = repository.getCompletionResponse("What is the age of the Universe?")
        Assert.assertEquals(result, "The Universe is too old for this flutter shit.")
    }

    @Test
    fun emptyTest() = runBlocking {
        val response = FakeResponse().emptyResponse
        coEvery { api.getGeneratedText(any()).isSuccessful } returns true
        coEvery { api.getGeneratedText(any()).body() } returns response
        val result = repository.getCompletionResponse("What is the age of the Universe?")
        Assert.assertEquals(result, "")
    }

    @Test
    fun failTest() = runBlocking {
        coEvery { api.getGeneratedText(any()).isSuccessful } returns false
        coEvery { api.getGeneratedText(any()).code() } returns 400
        val result = repository.getCompletionResponse("What is the age of the Universe?")
        Assert.assertEquals(result, "Error Code = 400")
    }
}