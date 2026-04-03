package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class CentrifugeReplyDto(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("connect")
    val connect: ConnectResultDto? = null,
    @SerialName("subscribe")
    val subscribe: SubscribeResultDto? = null,
    @SerialName("push")
    val push: PushEnvelopeDto? = null,
    @SerialName("error")
    val error: CentrifugeErrorDto? = null
)