package com.github.helenalog.ktsappkmp.feature.chat.presentation.model

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
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
    val avatar: UserAvatarUi,
    val attachments: List<ChatAttachmentUi> = emptyList(),
)