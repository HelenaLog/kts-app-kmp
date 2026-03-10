package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.data.remote.dto.ChannelDto
import com.github.helenalog.ktsappkmp.data.remote.dto.ConversationDto
import com.github.helenalog.ktsappkmp.data.remote.dto.MessageDto
import com.github.helenalog.ktsappkmp.data.remote.dto.MessageKindDto
import com.github.helenalog.ktsappkmp.data.remote.dto.StateDto
import com.github.helenalog.ktsappkmp.data.remote.dto.UserDto

@Composable
fun ConversationListItem(
    conversation: ConversationDto,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            NameAndTimeRow(
                name = conversation.user.fullName,
                time = conversation.formattedTime,
                isUnread = !conversation.isRead
            )
        },
        supportingContent = {
            MessagePreview(
                kind = conversation.lastMessage?.kind,
                text = conversation.lastMessage?.text.orEmpty(),
            )
        },
        leadingContent = {
            AvatarWithChannel(
                photoUrl = conversation.user.photo?.url,
                channelKind = conversation.channelKind,
            )
        }
    )
}

@Preview
@Composable
private fun ConversationListItemPreview() {
    ConversationListItem(
        conversation = ConversationDto(
            id = 1L,
            isRead = false,
            dateUpdated = "2024-03-09T15:12:00",
            user = UserDto(
                id = "u1",
                firstName = "Borodinsky",
                lastName = null,
                username = "borodinsky",
                photo = null
            ),
            channel = ChannelDto(
                id = "c1",
                kind = "tg",
                name = "Telegram"
            ),
            state = StateDto(
                stoppedByManager = false,
                operatorTagged = false,
                hasUnansweredOperatorMessage = true
            ),
            lastMessage = MessageDto(
                id = "m1",
                text = "Напиши, пожалуйста, имя и фамилию...",
                kind = MessageKindDto.BOT,
                dateCreated = "2024-03-09T15:12:00"
            )
        )
    )
}