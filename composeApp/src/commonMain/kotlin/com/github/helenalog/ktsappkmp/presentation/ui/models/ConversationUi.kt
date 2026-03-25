package com.github.helenalog.ktsappkmp.presentation.ui.models

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.domain.model.MessageKind

@Immutable
data class ConversationUi(
    val id: Long,
    val avatar: UserAvatarUi,
    val userName: String,
    val formattedTime: String,
    val isRead: Boolean,
    val lastMessageKind: MessageKind?,
    val lastMessageText: String,
    val channelKind: ChannelKind,
)