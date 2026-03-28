package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PublicationEnvelopeDto(
    @SerialName("data")
    val data: JsonElement? = null,
    @SerialName("offset")
    val offset: Long? = null
)