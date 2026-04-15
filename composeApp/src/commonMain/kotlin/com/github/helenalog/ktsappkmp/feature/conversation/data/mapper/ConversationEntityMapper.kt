package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.data.local.entity.ConversationEntity
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Channel
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

fun ConversationEntity.toDomain() = Conversation(
    id = id,
    isRead = isRead,
    userName = userName,
    photoUrl = photoUrl,
    channel = Channel(
        id = channelId,
        name = channelName,
        kind = ChannelKind.valueOf(channelKind),
        photoUrl = channelPhotoUrl
    ),
    lastMessageText = lastMessageText,
    lastMessageKind = lastMessageKind?.let { MessageKind.valueOf(it) },
    lastMessageAttachmentCount = lastMessageAttachmentCount,
    formattedTime = formattedTime,
    dateUpdated = dateUpdated,
    userId = userId,
)

fun Conversation.toEntity() = ConversationEntity(
    id = id,
    isRead = isRead,
    userName = userName,
    photoUrl = photoUrl,
    channelKind = channel.kind.name,
    channelId = channel.id,
    channelName = channel.name,
    channelPhotoUrl = channel.photoUrl,
    lastMessageText = lastMessageText,
    lastMessageKind = lastMessageKind?.name,
    lastMessageAttachmentCount = lastMessageAttachmentCount,
    formattedTime = formattedTime,
    dateUpdated = dateUpdated,
    userId = userId
)
