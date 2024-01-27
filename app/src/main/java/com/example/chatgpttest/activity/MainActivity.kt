package com.example.chatgpttest.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.chatgpttest.ChatScreen
import com.example.chatgpttest.di.DaggerApplicationComponent
import com.example.chatgpttest.ui.theme.ChatGPTTestTheme
import com.example.chatgpttest.viewmodel.ChatGPTViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val chatGPTViewModel: ChatGPTViewModel by viewModels { viewModelFactory }

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerApplicationComponent.create().inject(this)

        setContent {
            ChatGPTTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(chatGPTViewModel = chatGPTViewModel)
                }
            }
        }
    }
}