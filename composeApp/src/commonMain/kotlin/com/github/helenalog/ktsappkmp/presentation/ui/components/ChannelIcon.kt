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
import com.github.helenalog.ktsappkmp.data.remote.dto.ChannelKindDto
import com.github.helenalog.ktsappkmp.presentation.ui.theme.JivoColor
import com.github.helenalog.ktsappkmp.presentation.ui.theme.TelegramColor
import com.github.helenalog.ktsappkmp.presentation.ui.theme.WhatsAppColor

@Composable
fun ChannelIcon(
    kind: ChannelKindDto,
    modifier: Modifier = Modifier,
) {
    val icon: ImageVector = when (kind) {
        ChannelKindDto.TG -> Icons.Default.NearMe
        ChannelKindDto.WZ -> Icons.Default.Phone
        ChannelKindDto.JV -> Icons.Default.Email
        ChannelKindDto.UNKNOWN -> Icons.Default.Info
    }

    val tint = when (kind) {
        ChannelKindDto.TG -> TelegramColor
        ChannelKindDto.WZ -> WhatsAppColor
        ChannelKindDto.JV -> JivoColor
        ChannelKindDto.UNKNOWN -> MaterialTheme.colorScheme.primary
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
    ChannelIcon(kind = ChannelKindDto.TG)
}