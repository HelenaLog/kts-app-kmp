package com.github.helenalog.ktsappkmp.feature.chat.data.mapper

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.WsNewMessagePayloadDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import toDomain

class WsChatMapper {
    fun map(payload: WsNewMessagePayloadDto): ChatMessage? =
        payload.data?.toDomain()
}
