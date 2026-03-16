package com.github.helenalog.ktsappkmp.data.mapper

import com.github.helenalog.ktsappkmp.data.local.entity.ConversationEntity
import com.github.helenalog.ktsappkmp.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.domain.model.Conversation
import com.github.helenalog.ktsappkmp.domain.model.MessageKind

fun ConversationEntity.toDomain() = Conversation(
    id = id,
    isRead = isRead,
    userName = userName,
    photoUrl = photoUrl,
    channelKind = ChannelKind.valueOf(channelKind),
    lastMessageText = lastMessageText,
    lastMessageKind = lastMessageKind?.let { MessageKind.valueOf(it) },
    formattedTime = formattedTime,
    dateUpdated = dateUpdated,
)

fun Conversation.toEntity() = ConversationEntity(
    id = id,
    isRead = isRead,
    userName = userName,
    photoUrl = photoUrl,
    channelKind = channelKind.name,
    lastMessageText = lastMessageText,
    lastMessageKind = lastMessageKind?.name,
    formattedTime = formattedTime,
    dateUpdated = dateUpdated,
)