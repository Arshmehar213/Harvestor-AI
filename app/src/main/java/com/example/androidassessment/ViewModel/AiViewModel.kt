package com.example.androidassessment.ViewModel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassessment.AiService.VertexService
import com.example.androidassessment.Models.ChatMessage
import com.example.androidassessment.Models.ChatModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response

class AiViewModel(application: Application) : AndroidViewModel(application) {

    private val vertexAIService = VertexService(application)

    private val _uiState = MutableStateFlow(ChatModel())
    val uiState: StateFlow<ChatModel> = _uiState.asStateFlow()

    private val _Response= MutableStateFlow(ChatModel())
    val Response : StateFlow<ChatModel> = _Response.asStateFlow()

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        val userMessage = ChatMessage(text = message, isUser = true)

        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + userMessage,
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            vertexAIService.textGeneration(message)
                .onSuccess { response ->
                    val aiMessage = ChatMessage(text = response, isUser = false)
                    _uiState.value = _uiState.value.copy(
                        messages = _uiState.value.messages + aiMessage,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }

    fun sendImageInput(bitmap: Bitmap , prompt : String){
        viewModelScope.launch {
            vertexAIService.ImageInput(bitmap , prompt)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
