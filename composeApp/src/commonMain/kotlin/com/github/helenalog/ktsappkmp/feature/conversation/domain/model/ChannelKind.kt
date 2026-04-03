package com.github.helenalog.ktsappkmp.feature.conversation.domain.model

import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.ic_channel_tg
import org.jetbrains.compose.resources.DrawableResource

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
