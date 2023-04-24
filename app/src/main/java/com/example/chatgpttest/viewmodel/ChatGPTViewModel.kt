package com.example.chatgpttest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgpttest.data.repository.ChatGPTRepository
import com.example.chatgpttest.model.Choice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatGPTViewModel @Inject constructor(
    private val repository: ChatGPTRepository
) :
    ViewModel() {

    private val _conversations: MutableStateFlow<MutableList<Choice>> = MutableStateFlow(
        mutableListOf()
    )
    val conversationsState: StateFlow<MutableList<Choice>> = _conversations.asStateFlow()

    fun resetChat() {
        _conversations.value.clear()
    }

    fun updateChat(choice: Choice) {
        viewModelScope.launch {
            changeMessage(choice, isAdding = true)
            changeMessage(Choice("Typing..."), isAdding = true)
            val result = repository.getCompletionResponse(choice.text)
            changeMessage(Choice("Typing..."), isAdding = false)
            changeMessage(Choice(result.trim()), isAdding = true)
        }
    }

    private fun changeMessage(choice: Choice, isAdding: Boolean) {
        val newList = _conversations.value.toMutableList()
        if (isAdding) {
            newList.add(choice)
        } else {
            try {
                newList.remove(choice)
            } catch (error: IndexOutOfBoundsException) {
                Log.d(
                    "ChatGPTViewModel",
                    "$error. Please, do not remove message from empty list."
                )
            }
        }
        _conversations.value = newList
    }
}