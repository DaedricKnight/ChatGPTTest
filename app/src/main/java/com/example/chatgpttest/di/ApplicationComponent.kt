package com.example.chatgpttest.di

import com.example.chatgpttest.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}