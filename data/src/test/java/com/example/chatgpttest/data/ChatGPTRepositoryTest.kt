package com.example.chatgpttest.data

import com.example.chatgpttest.data.api.ChatGPTApi
import com.example.chatgpttest.data.model.FakeResponse
import com.example.chatgpttest.data.repository.ChatGPTRepositoryImpl
import com.example.chatgpttest.domain.repository.ChatGPTRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Call
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
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
        val result = repository.getCompletionResponse("What is the age of the Universe?", 2048, 1)
        Assert.assertEquals(
            result.choices[0].text,
            "The Universe is too old for this flutter shit."
        )
    }

    @Test
    fun successFlowTest() = runBlocking {
        val response = Response.success(
            "The Universe is too old for this flutter shit.".toResponseBody(
                null
            )
        )
        val callMock: Call<ResponseBody> = mockk()
        coEvery { api.getFlowText(any()) } returns callMock
        coEvery { callMock.execute() } returns response
        val result = repository.getMessagesFlow("What is the age of the Universe?", 2048, 1)
        val numbers = result.toList()
        Assert.assertEquals(numbers.size, 0)
    }

    @Test
    fun successFlowErrorTest() = runBlocking {
        val errorMessage = "Error message"
        val errorCode = 400 // Replace with a suitable error code
        val errorBody = "{\"error\": \"$errorMessage\"}"

        val response = Response.error<ResponseBody>(errorCode,
            errorBody.toResponseBody(
                null
            )
        )
        val callMock: Call<ResponseBody> = mockk()
        coEvery { api.getFlowText(any()) } returns callMock
        coEvery { callMock.execute() } returns response
        val result = repository.getMessagesFlow("What is the age of the Universe?", 2048, 1)
        val numbers = result.toList()
        Assert.assertEquals(numbers.size, 1)
        Assert.assertEquals(numbers[0], "Failure! Try again. Error: {\"error\":\"Error message\"}")
    }

    @Test
    fun emptyTest() = runBlocking {
        val response = FakeResponse().emptyResponse
        coEvery { api.getGeneratedText(any()).isSuccessful } returns true
        coEvery { api.getGeneratedText(any()).body() } returns response
        val result = repository.getCompletionResponse("What is the age of the Universe?", 2048, 1)
        Assert.assertEquals(result.choices[0].text, "")
    }

    @Test
    fun failTest() = runBlocking {
        coEvery { api.getGeneratedText(any()).isSuccessful } returns false
        coEvery { api.getGeneratedText(any()).code() } returns 400
        val result = repository.getCompletionResponse("What is the age of the Universe?", 2048, 1)
        Assert.assertEquals(result.choices[0].text, "Error Code = 400, message = ")
    }
}