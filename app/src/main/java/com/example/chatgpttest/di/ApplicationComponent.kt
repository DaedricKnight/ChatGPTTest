package com.example.chatgpttest.di

import com.example.chatgpttest.activity.MainActivity
import com.example.chatgpttest.data.di.NetworkModule
import com.example.chatgpttest.data.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}