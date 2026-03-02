package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.Friend


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
            OnlineStatus(isOnline = friend.online == 1)
        },
        leadingContent = {
            FriendAvatar(photoUrl = friend.photo)
        }
    )
}

@Preview
@Composable
private fun FriendListItemOnlinePreview() {
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
private fun FriendListItemOfflinePreview() {
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