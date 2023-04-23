package com.example.chatgpttest.repository

import okhttp3.Interceptor
import okhttp3.Response

private const val apiKey = "YOU-API-KEY"

class AuthenticationInterceptor:  Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}