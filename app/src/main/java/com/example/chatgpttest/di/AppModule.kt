package com.example.chatgpttest.di

import androidx.lifecycle.ViewModelProvider
import com.example.chatgpttest.viewmodel.ChatGPTViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideChatGPTViewModelFactory(chatGPTViewModelFactory: ChatGPTViewModelFactory): ViewModelProvider.Factory {
        return chatGPTViewModelFactory
    }
}