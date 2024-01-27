package com.example.chatgpttest

import android.app.Application
import com.example.chatgpttest.di.AppModule
import com.example.chatgpttest.di.ApplicationComponent
import com.example.chatgpttest.di.DaggerApplicationComponent

class ChatGPTApplication : Application() {
    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
        DaggerApplicationComponent.builder()
            .appModule(AppModule())
            .build()
    }
}
