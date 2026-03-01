package com.github.helenalog.ktsappkmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.screens.main.presentation.models.Friend
import com.github.helenalog.ktsappkmp.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.ui.theme.OfflineColor
import com.github.helenalog.ktsappkmp.ui.theme.OnlineColor
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.main_status_online
import ktsappkmp.composeapp.generated.resources.main_status_offline
import org.jetbrains.compose.resources.stringResource


@Composable
fun FriendListItem(
    modifier: Modifier = Modifier,
    friend: Friend
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = "${friend.firstName} ${friend.lastName}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        },
        supportingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.onlineIndicatorSpacing)
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimensions.onlineIndicatorSize)
                        .clip(CircleShape)
                        .background(
                            if (friend.online == 1) OnlineColor else OfflineColor
                        )
                )
                Text(
                    text = if (friend.online == 1) stringResource(Res.string.main_status_online) else stringResource(Res.string.main_status_offline),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (friend.online == 1) OnlineColor else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        leadingContent = {
            FriendAvatar(photoUrl = friend.photo)
        }
    )
}

@Preview
@Composable
private fun FriendCardOnlinePreview() {
    FriendListItem(
        friend = Friend(
            id = 1,
            firstName = "Иван",
            lastName = "Иванов",
            photo = null,
            online = 1
        )
    )
}

@Preview
@Composable
private fun FriendCardOfflinePreview() {
    FriendListItem(
        friend = Friend(
            id = 2,
            firstName = "Мария",
            lastName = "Петрова",
            photo = null,
            online = 0
        )
    )
}