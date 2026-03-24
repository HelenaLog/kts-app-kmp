package com.github.helenalog.ktsappkmp.feature.chat.domain.model

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

data class ChatMessage(
    val id: String,
    val kind: MessageKind,
    val text: String? = null,
    val time: String? = null,
    val date: String? = null,
    val attachments: List<ChatAttachment> = emptyList(),
)