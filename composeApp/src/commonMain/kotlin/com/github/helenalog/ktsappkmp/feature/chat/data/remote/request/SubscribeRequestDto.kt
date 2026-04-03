package com.github.helenalog.ktsappkmp.feature.chat.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscribeRequestDto(
    @SerialName("channel")
    val channel: String,
    @SerialName("token")
    val token: String
)
