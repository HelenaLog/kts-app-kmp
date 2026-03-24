package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.text.input.TextFieldState
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel

class ChatViewModel() : BaseViewModel<ChatUiState, Nothing>(initialState = ChatUiState()) {
    val messageInputState = TextFieldState()
}