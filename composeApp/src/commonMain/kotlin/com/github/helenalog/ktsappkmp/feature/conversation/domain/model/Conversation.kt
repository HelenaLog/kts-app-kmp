package com.github.helenalog.ktsappkmp.feature.conversation.domain.model

data class Conversation(
    val id: Long,
    val isRead: Boolean,
    val userName: String,
    val photoUrl: String?,
    val channel: Channel,
    val lastMessageText: String,
    val lastMessageKind: MessageKind?,
    val formattedTime: String,
    val dateUpdated: String,
    val userId: String,
)
