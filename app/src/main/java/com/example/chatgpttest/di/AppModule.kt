package com.example.chatgpttest.di

import androidx.lifecycle.ViewModelProvider
import com.example.chatgpttest.data.ChatGPTApi
import com.example.chatgpttest.viewmodel.ChatGPTViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideChatGPTApi(retrofit: Retrofit): ChatGPTApi {
        return retrofit.create(ChatGPTApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChatGPTViewModelFactory(chatGPTViewModelFactory: ChatGPTViewModelFactory): ViewModelProvider.Factory {
        return chatGPTViewModelFactory
    }
}