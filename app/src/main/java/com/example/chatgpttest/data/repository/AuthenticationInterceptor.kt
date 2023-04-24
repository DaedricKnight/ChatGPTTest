package com.example.chatgpttest.data.repository

import com.example.chatgpttest.constants.apiKey
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor:  Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}