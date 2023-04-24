package com.example.chatgpttest.di

import androidx.lifecycle.ViewModelProvider
import com.example.chatgpttest.constants.baseUrl
import com.example.chatgpttest.data.api.ChatGPTApi
import com.example.chatgpttest.data.repository.AuthenticationInterceptor
import com.example.chatgpttest.viewmodel.ChatGPTViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideChatGPTViewModelFactory(chatGPTViewModelFactory: ChatGPTViewModelFactory): ViewModelProvider.Factory {
        return chatGPTViewModelFactory
    }
}