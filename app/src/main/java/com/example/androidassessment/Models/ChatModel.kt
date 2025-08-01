package com.example.androidassessment.Models

data class ChatModel(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
