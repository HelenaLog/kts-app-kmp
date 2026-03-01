package com.github.helenalog.ktsappkmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.ui.theme.OfflineColor
import com.github.helenalog.ktsappkmp.ui.theme.OnlineColor
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.main_status_offline
import ktsappkmp.composeapp.generated.resources.main_status_online
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnlineStatus(
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    val statusColor = if (isOnline) OnlineColor else OfflineColor
    val statusText = if (isOnline) stringResource(Res.string.main_status_online)
    else stringResource(Res.string.main_status_offline)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.onlineIndicatorSpacing)
    ) {
        Box(
            modifier = Modifier
                .size(Dimensions.onlineIndicatorSize)
                .clip(CircleShape)
                .background(statusColor)
        )
        Text(
            text = statusText,
            style = MaterialTheme.typography.bodySmall,
            color = if (isOnline) OnlineColor else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun OnlineStatusPreview() {
    Column {
        OnlineStatus(true)
        OnlineStatus(false)
    }
}