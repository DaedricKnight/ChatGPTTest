package com.example.chatgpttest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.chatgpttest.model.Choice
import com.example.chatgpttest.viewmodel.ChatGPTViewModel

@Composable
fun ChatScreen(
    chatGPTViewModel: ChatGPTViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ChatList(
            chatGPTViewModel = chatGPTViewModel,
            modifier = Modifier.height(760.dp),
        )
        CharInput(
            chatGPTViewModel = chatGPTViewModel,
            modifier = Modifier
        )
    }
}

@Composable
fun ChatList(
    chatGPTViewModel: ChatGPTViewModel,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomEnd
        ) {
            LazyColumn {
                items(chatGPTViewModel.itemList.size) { item ->
                    ChatBubbleView(item % 2 == 0, Choice(chatGPTViewModel.itemList[item].text))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBubbleView(isLeft: Boolean, message: Choice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when {
            !isLeft -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 340.dp),
            shape = cardShapeFor(isLeft),
            colors = CardDefaults.cardColors(
                containerColor = if (isLeft) Color(0xFF1E88E5) else Color(0xFF7CB342),
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = message.text,
                color = when {
                    !isLeft -> MaterialTheme.colorScheme.onPrimary
                    else -> MaterialTheme.colorScheme.onSecondary
                },
            )
        }
    }
}

@Composable
fun cardShapeFor(isLeft: Boolean): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        !isLeft -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun CharInput(
    chatGPTViewModel: ChatGPTViewModel,
    modifier: Modifier = Modifier,
) {
    var value by remember {
        mutableStateOf("")
    }

    fun sendMessage() {
        chatGPTViewModel.updateChat(Choice(value))
        value = ""
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { value = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
        Button(
            modifier = Modifier.height(58.dp),
            onClick = { sendMessage() },
            enabled = value.isNotBlank(),
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun BottomBar(
    chatGPTViewModel: ChatGPTViewModel,
) {
    var value by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp)),
                label = { Text("Write here") }
            )

            IconButton(
                onClick = { /* handle send button click here */ },
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_send_24),
                    contentDescription = "Send"
                )
            }
        }
    }
}