package com.github.helenalog.ktsappkmp.feature.chat.domain.model

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

data class ConversationDetail(
    val userId: String,
    val userName: String,
    val photoUrl: String?,
    val channelId: String,
    val botName: String,
    val channelPhoto: String,
    val channelKind: ChannelKind,
    val stoppedByManager: Boolean,
)
