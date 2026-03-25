package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.domain.model.MessageKind
import com.github.helenalog.ktsappkmp.presentation.ui.models.ConversationUi
import com.github.helenalog.ktsappkmp.presentation.ui.models.UserAvatarUi
import com.github.helenalog.ktsappkmp.presentation.ui.theme.AppTheme

@Composable
fun ConversationListItem(
    conversation: ConversationUi,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            NameAndTimeRow(
                name = conversation.userName,
                time = conversation.formattedTime,
                isUnread = !conversation.isRead
            )
        },
        supportingContent = {
            MessagePreview(
                kind = conversation.lastMessageKind,
                text = conversation.lastMessageText,
            )
        },
        leadingContent = {
            AvatarWithChannel(
                avatar = conversation.avatar,
                channelKind = conversation.channelKind,
            )
        }
    )
}

@Preview
@Composable
private fun ConversationListItemPreview() {
    AppTheme {
        ConversationListItem(
            conversation = ConversationUi(
                id = 1L,
                isRead = false,
                formattedTime = "15:12",
                userName = "Borodinsky",
                avatar = UserAvatarUi(
                    initials = "B",
                    photoUrl = null
                ),
                channelKind = ChannelKind.TG,
                lastMessageText = "Напиши, пожалуйста, имя и фамилию...",
                lastMessageKind = MessageKind.BOT,
            )
        )
    }
}