package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageKindDto
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

fun MessageKindDto.toDomain(): MessageKind = when (this) {
    MessageKindDto.BOT -> MessageKind.BOT
    MessageKindDto.SERVICE -> MessageKind.SERVICE
    MessageKindDto.MANAGER -> MessageKind.MANAGER
    MessageKindDto.USER -> MessageKind.USER
}
