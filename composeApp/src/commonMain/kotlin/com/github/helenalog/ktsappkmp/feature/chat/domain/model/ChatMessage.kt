package com.github.helenalog.ktsappkmp.feature.chat.domain.model

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import kotlinx.datetime.Instant

data class ChatMessage(
    val id: String,
    val kind: MessageKind,
    val text: String? = null,
    val time: String? = null,
    val date: String? = null,
    val createdAt: Instant = Instant.DISTANT_PAST,
    val attachments: List<ChatAttachment> = emptyList(),
)
