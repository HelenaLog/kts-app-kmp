package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscribeResultDto(
    @SerialName("channel")
    val channel: String? = null,
    @SerialName("recoverable")
    val recoverable: Boolean = false,
    @SerialName("epoch")
    val epoch: String? = null
)
