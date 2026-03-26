package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationUi
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

@Composable
fun ConversationListItem(
    onConversationClick: (ConversationUi) -> Unit,
    conversation: ConversationUi,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier.clickable {
            onConversationClick(conversation)
        },
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
                userId = "1"
            ),
            onConversationClick = {}
        )
    }
}