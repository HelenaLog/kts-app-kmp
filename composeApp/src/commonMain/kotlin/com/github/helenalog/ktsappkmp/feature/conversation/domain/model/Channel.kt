package com.github.helenalog.ktsappkmp.feature.conversation.domain.model

import androidx.compose.runtime.Immutable

data class Channel(
    val id: String,
    val name: String,
    val kind: ChannelKind,
    val photoUrl: String?
)
