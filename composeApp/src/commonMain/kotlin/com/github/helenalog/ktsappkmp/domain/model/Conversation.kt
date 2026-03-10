package com.github.helenalog.ktsappkmp.domain.model

data class Conversation(
    val id: Long,
    val isRead: Boolean,
    val userName: String,
    val photoUrl: String?,
    val channelKind: ChannelKind,
    val lastMessageText: String,
    val lastMessageKind: MessageKind?,
    val formattedTime: String,
)

enum class ChannelKind {
    TG, WZ, JV, UNKNOWN;

    val displayName: String
        get() = when (this) {
            TG -> "Telegram"
            WZ -> "WhatsApp"
            JV -> "Jivo"
            UNKNOWN -> "Unknown"
        }
}
enum class MessageKind { BOT, SERVICE, MANAGER, USER }