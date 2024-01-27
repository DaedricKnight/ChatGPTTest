package com.example.chatgpttest.domain.model

import com.google.gson.annotations.SerializedName

enum class Role(val value: String) {
    @SerializedName("system")
    system("system"),
    @SerializedName("assistant")
    assistant("assistant"),
    @SerializedName("user")
    user("user")
}