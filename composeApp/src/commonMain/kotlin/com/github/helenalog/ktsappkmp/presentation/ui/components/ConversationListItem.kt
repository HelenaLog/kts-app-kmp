package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.domain.model.Conversation
import com.github.helenalog.ktsappkmp.domain.model.MessageKind

@Composable
fun ConversationListItem(
    conversation: Conversation,
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
                photoUrl = conversation.photoUrl,
                channelKind = conversation.channelKind,
            )
        }
    )
}

@Preview
@Composable
private fun ConversationListItemPreview() {
    ConversationListItem(
        conversation = Conversation(
            id = 1L,
            isRead = false,
            formattedTime = "15:12",
            userName = "Borodinsky",
            photoUrl = null,
            channelKind = ChannelKind.TG,
            lastMessageText = "Напиши, пожалуйста, имя и фамилию...",
            lastMessageKind = MessageKind.BOT,
        )
    )
}