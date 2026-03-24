package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

@Immutable
data class ChatMessageUi(
    val id: String,
    val text: String,
    val formattedTime: String,
    val isOutgoing: Boolean,
    val kind: MessageKind,
    val userName: String,
    val userPhotoUrl: String,
    val attachments: List<ChatAttachmentUi> = emptyList(),
)