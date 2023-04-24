package com.example.chatgpttest.di

import com.example.chatgpttest.data.repository.ChatGPTRepository
import com.example.chatgpttest.data.repository.ChatGPTRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun openAIRepository(
        repo: ChatGPTRepositoryImpl
    ): ChatGPTRepository
}