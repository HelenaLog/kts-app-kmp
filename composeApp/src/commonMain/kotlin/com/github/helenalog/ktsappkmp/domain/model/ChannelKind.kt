package com.github.helenalog.ktsappkmp.domain.model

enum class ChannelKind {
    TG,
    WZ,
    JV,
    UNKNOWN;

    val displayName: String
        get() = when (this) {
            TG -> "Telegram"
            WZ -> "WhatsApp"
            JV -> "Jivo"
            UNKNOWN -> "Unknown"
        }

    companion object {
        fun fromString(value: String): ChannelKind =
            entries.firstOrNull { it.name.lowercase() == value.lowercase() } ?: UNKNOWN
    }
}