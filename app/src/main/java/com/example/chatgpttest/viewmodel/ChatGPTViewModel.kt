package com.example.chatgpttest.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgpttest.model.Choice
import com.example.chatgpttest.repository.ChatGPTRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatGPTViewModel @Inject constructor(private val repository: ChatGPTRepository) :
    ViewModel() {

    val itemList = mutableStateListOf<Choice>()

    fun resetChat() {
        itemList.clear()
    }

    fun updateChat(choice: Choice) {
        viewModelScope.launch {
            itemList.add(choice)
            itemList.add(Choice("Typing..."))
            val result = repository.getResponse(choice.text)?.choices
            itemList.remove(Choice("Typing..."))
            itemList.add(Choice(result?.get(0)?.text?.trim() ?: ""))
        }
    }
}