package com.github.helenalog.ktsappkmp.feature.chat.data.mapper

import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.WsNewMessagePayloadDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage

class WsMessageMapper(
    private val dateTimeParser: DateTimeParser
) {
    fun map(payload: WsNewMessagePayloadDto): ChatMessage? =
        payload.data?.toDomain(dateTimeParser)
}
