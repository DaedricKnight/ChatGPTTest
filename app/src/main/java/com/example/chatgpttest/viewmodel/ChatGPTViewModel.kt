package com.example.chatgpttest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgpttest.data.constants.errorTag
import com.example.chatgpttest.domain.model.ChatGPTChoice
import com.example.chatgpttest.domain.usecase.GetCompletionResponseUseCase
import com.example.chatgpttest.domain.usecase.GetMessagesFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatGPTViewModel @Inject constructor(
    private val getCompletionResponseUseCase: GetCompletionResponseUseCase,
    private val getMessagesFlowUseCase: GetMessagesFlowUseCase
) : ViewModel() {

    private val _conversations: MutableStateFlow<MutableList<ChatGPTChoice>> =
        MutableStateFlow(
            mutableListOf()
        )
    val conversationsState: StateFlow<MutableList<ChatGPTChoice>> =
        _conversations.asStateFlow()

    fun resetChat() {
        _conversations.value.clear()
    }

    fun updateChat(choice: ChatGPTChoice) {
        viewModelScope.launch {
            changeMessage(choice, isAdding = true)
            val typing = ChatGPTChoice(
                "Typing...",
                index = 0,
                isChatGPT = true
            )
            changeMessage(typing, isAdding = true)
            val result = getCompletionResponseUseCase.getCompletionResponse(choice.text, 100, 10)
            changeMessage(typing, isAdding = false)
            for (i in result.choices.indices) {
                changeMessage(
                    ChatGPTChoice(
                        result.choices[i].text.trim(),
                        index = 1 + i,
                        isChatGPT = true
                    ), isAdding = true
                )
            }
        }
    }

    fun updateChatStream(choice: ChatGPTChoice) {
        viewModelScope.launch {
            changeMessage(choice, isAdding = true)
            val typing = ChatGPTChoice(
                "Typing...",
                index = 0,
                isChatGPT = true
            )
            changeMessage(typing, isAdding = true)
            val flowResult: Flow<String> =
                getMessagesFlowUseCase.getMessageFlow(choice.text, 2048, 1)
            var stringResult = ""
            flowResult.collectIndexed { index, value ->
                if (index == 0) {
                    changeMessage(typing, isAdding = false)
                }
                addAnswer(stringResult, index = index, isAdding = false)
                stringResult += value
                addAnswer(stringResult, index = 1 + index, isAdding = true)
            }
        }
    }

    private fun addAnswer(stringResult: String, index: Int, isAdding: Boolean) {
        changeMessage(
            ChatGPTChoice(
                stringResult,
                index = index,
                isChatGPT = true
            ), isAdding = isAdding
        )
    }

    private fun changeMessage(
        choice: ChatGPTChoice,
        isAdding: Boolean
    ) {
        val newList = _conversations.value.toMutableList()
        if (isAdding) {
            newList.add(choice)
        } else {
            try {
                newList.remove(choice)
            } catch (error: IndexOutOfBoundsException) {
                Log.d(
                    errorTag,
                    "$error. Please, do not remove message from empty list."
                )
            }
        }
        _conversations.value = newList
    }
}