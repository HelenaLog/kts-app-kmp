package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class ChatUiState(
    val messages: List<ChatListItemUi> = emptyList(),
    val pendingAttachments: List<ChatAttachmentUi> = emptyList()
)