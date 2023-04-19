package com.example.chatgpttest.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Provider


class ChatGPTViewModelFactory @Inject constructor(
    private val chatGPTViewModelProvider: Provider<ViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return chatGPTViewModelProvider.get() as T
    }
}