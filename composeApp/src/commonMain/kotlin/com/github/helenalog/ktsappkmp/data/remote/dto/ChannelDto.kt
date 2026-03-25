package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelDto(
    @SerialName("_id")
    val id: String,
    @SerialName("kind")
    val kind: String,
    @SerialName("name")
    val name: String
)