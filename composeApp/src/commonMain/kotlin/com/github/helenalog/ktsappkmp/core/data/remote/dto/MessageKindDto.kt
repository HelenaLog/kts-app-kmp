package com.github.helenalog.ktsappkmp.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MessageKindDto {
    @SerialName("bot") BOT,
    @SerialName("service") SERVICE,
    @SerialName("manager") MANAGER,
    @SerialName("user") USER
}