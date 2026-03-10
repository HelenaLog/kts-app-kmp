package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChannelKindDto {
    @SerialName("tg") TG,
    @SerialName("wz") WZ,
    @SerialName("jv") JV,
    @SerialName("unknown") UNKNOWN;

    val displayName: String
        get() = when (this) {
            TG -> "Telegram"
            WZ -> "WhatsApp"
            JV -> "Jivo"
            UNKNOWN -> "Unknown"
        }

    companion object {
        fun fromString(value: String): ChannelKindDto =
            entries.firstOrNull { it.name.lowercase() == value.lowercase() } ?: UNKNOWN
    }
}