package com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.ic_channel_jivo
import ktsappkmp.composeapp.generated.resources.ic_channel_max
import ktsappkmp.composeapp.generated.resources.ic_channel_tg
import ktsappkmp.composeapp.generated.resources.ic_channel_viber
import ktsappkmp.composeapp.generated.resources.ic_channel_vk
import ktsappkmp.composeapp.generated.resources.ic_channel_widget
import ktsappkmp.composeapp.generated.resources.ic_channel_wz
import org.jetbrains.compose.resources.DrawableResource

val ChannelKind.displayName: String
    get() = when (this) {
        ChannelKind.TG -> "Telegram"
        ChannelKind.WZ -> "Wazzup24"
        ChannelKind.JV -> "JivoChat"
        ChannelKind.MAX -> "MAX"
        ChannelKind.VIBER -> "Viber"
        ChannelKind.WIDGET -> "Виджет"
        ChannelKind.VK -> "Вконтакте"
        ChannelKind.UNKNOWN -> "Unknown"
    }

val ChannelKind.icon: DrawableResource
    get() = when (this) {
        ChannelKind.TG -> Res.drawable.ic_channel_tg
        ChannelKind.WZ -> Res.drawable.ic_channel_wz
        ChannelKind.JV -> Res.drawable.ic_channel_jivo
        ChannelKind.MAX -> Res.drawable.ic_channel_max
        ChannelKind.VIBER -> Res.drawable.ic_channel_viber
        ChannelKind.WIDGET -> Res.drawable.ic_channel_widget
        ChannelKind.VK -> Res.drawable.ic_channel_vk
        ChannelKind.UNKNOWN -> Res.drawable.ic_channel_widget
    }
