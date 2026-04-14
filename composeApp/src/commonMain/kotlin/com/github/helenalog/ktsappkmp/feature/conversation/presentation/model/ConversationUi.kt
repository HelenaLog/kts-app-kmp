package com.github.helenalog.ktsappkmp.feature.conversation.presentation.model

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

@Immutable
data class ConversationUi(
    val id: Long,
    val avatar: UserAvatarUi,
    val userName: String,
    val formattedTime: String,
    val isRead: Boolean,
    val lastMessageKind: MessageKind?,
    val lastMessageAttachmentCount: Int,
    val lastMessageText: String,
    val channelKind: ChannelKind,
    val userId: String
)
