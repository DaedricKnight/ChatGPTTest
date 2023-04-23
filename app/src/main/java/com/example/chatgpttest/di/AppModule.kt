package com.example.chatgpttest.di

import androidx.lifecycle.ViewModelProvider
import com.example.chatgpttest.constants.baseUrl
import com.example.chatgpttest.data.ChatGPTApi
import com.example.chatgpttest.repository.AuthenticationInterceptor
import com.example.chatgpttest.viewmodel.ChatGPTViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(AuthenticationInterceptor())
        builder.retryOnConnectionFailure(false)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
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