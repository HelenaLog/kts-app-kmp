package com.github.helenalog.ktsappkmp.feature.conversation.domain.model

import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.ic_channel_tg
import org.jetbrains.compose.resources.DrawableResource

data class Conversation(
    val id: Long,
    val isRead: Boolean,
    val userName: String,
    val photoUrl: String?,
    val channelKind: ChannelKind,
    val lastMessageText: String,
    val lastMessageKind: MessageKind?,
    val formattedTime: String,
    val dateUpdated: String,
    val userId: String
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

    val icon: DrawableResource
        get() = when (this) {
        TG -> Res.drawable.ic_channel_tg
        WZ -> Res.drawable.ic_channel_tg
        JV -> Res.drawable.ic_channel_tg
        UNKNOWN -> Res.drawable.ic_channel_tg
    }
}
enum class MessageKind { BOT, SERVICE, MANAGER, USER }