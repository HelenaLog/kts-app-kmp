package com.github.helenalog.ktsappkmp.feature.conversation.domain.model
import org.jetbrains.compose.resources.DrawableResource

import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.ic_channel_tg

data class Conversation(
    val id: Long,
    val isRead: Boolean,
    val userName: String,
    val photoUrl: String?,
    val channelKind: ChannelKind,
    val lastMessageText: String,
    val lastMessageKind: MessageKind?,
    val formattedTime: String,
    val dateUpdated: String,
    val userId: String
)

