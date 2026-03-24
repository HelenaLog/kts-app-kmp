package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class ChatUiState(
    var messages: List<ChatListItemUi> = emptyList()
)