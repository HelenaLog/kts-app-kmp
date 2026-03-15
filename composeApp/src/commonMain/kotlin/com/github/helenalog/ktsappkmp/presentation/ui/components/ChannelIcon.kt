package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.presentation.ui.theme.JivoColor
import com.github.helenalog.ktsappkmp.presentation.ui.theme.TelegramColor
import com.github.helenalog.ktsappkmp.presentation.ui.theme.WhatsAppColor


@Composable
fun ChannelIcon(
    kind: ChannelKind,
    modifier: Modifier = Modifier,
) {
    val icon: ImageVector = when (kind) {
        ChannelKind.TG -> Icons.Default.NearMe
        ChannelKind.WZ -> Icons.Default.Phone
        ChannelKind.JV -> Icons.Default.Email
        ChannelKind.UNKNOWN -> Icons.Default.Info
    }

    val tint = when (kind) {
        ChannelKind.TG -> TelegramColor
        ChannelKind.WZ -> WhatsAppColor
        ChannelKind.JV -> JivoColor
        ChannelKind.UNKNOWN -> MaterialTheme.colorScheme.primary
    }

    Icon(
        imageVector = icon,
        contentDescription = kind.displayName,
        modifier = modifier,
        tint = tint,
    )
}

@Preview
@Composable
private fun ChannelIconPreview() {
    ChannelIcon(kind = ChannelKind.TG)
}