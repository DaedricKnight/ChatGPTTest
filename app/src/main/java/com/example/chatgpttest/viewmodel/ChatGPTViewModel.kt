package com.example.chatgpttest.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chatgpttest.model.Choice
import com.example.chatgpttest.repository.ChatGPTRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatGPTViewModel @Inject constructor(private val repository: ChatGPTRepository) :
    ViewModel() {

    val itemList = mutableStateListOf<Choice>()

    fun resetChat() {
        itemList.clear()
    }

    fun updateChat(choice: Choice) {
        CoroutineScope(Dispatchers.IO).launch {
            itemList.add(choice)
            itemList.add(Choice("Typing..."))
            val result = repository.getCompletionResponse(choice.text)
            itemList.remove(Choice("Typing..."))
            itemList.add(Choice(result.trim()))
        }
    }
}