package com.example.chatgpttest.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chatgpttest.model.Choice
import com.example.chatgpttest.repository.sendRequest2ToChatGPT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    val itemList = mutableStateListOf<Choice>()
    val mainScope = CoroutineScope(Dispatchers.IO)

    fun resetChat() {
        itemList.clear()
    }

    fun updateChat(choice: Choice) {
        mainScope.launch {
            itemList.add(choice)
            itemList.add(Choice("Typing..."))
            val result = sendRequest2ToChatGPT(choice.text)
            itemList.remove(Choice("Typing..."))
            itemList.add(Choice(result[0].text.trim()))
        }
    }
}