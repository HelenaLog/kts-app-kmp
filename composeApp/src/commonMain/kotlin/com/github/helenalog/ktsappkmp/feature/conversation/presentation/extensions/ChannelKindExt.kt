package com.github.helenalog.ktsappkmp.feature.conversation.presentation.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.JivoColor
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.TelegramColor
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.WhatsAppColor
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

val ChannelKind.displayName: String
    get() = when (this) {
        ChannelKind.TG -> "Telegram"
        ChannelKind.WZ -> "WhatsApp"
        ChannelKind.JV -> "Jivo"
        ChannelKind.UNKNOWN -> "Unknown"
    }

val ChannelKind.icon: ImageVector
    get() = when (this) {
        ChannelKind.TG -> Icons.Default.NearMe
        ChannelKind.WZ -> Icons.Default.Phone
        ChannelKind.JV -> Icons.Default.Email
        ChannelKind.UNKNOWN -> Icons.Default.Info
    }

val ChannelKind.color: Color
    get() = when (this) {
        ChannelKind.TG -> TelegramColor
        ChannelKind.WZ -> WhatsAppColor
        ChannelKind.JV -> JivoColor
        ChannelKind.UNKNOWN -> Color.Unspecified
    }