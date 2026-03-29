package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PushEnvelopeDto(
    @SerialName("channel")
    val channel: String? = null,
    @SerialName("pub")
    val pub: PublicationEnvelopeDto? = null
)
