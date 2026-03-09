package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.ChannelDto
import com.github.helenalog.ktsappkmp.domain.model.ConversationDto
import com.github.helenalog.ktsappkmp.domain.model.MessageDto
import com.github.helenalog.ktsappkmp.domain.model.StateDto
import com.github.helenalog.ktsappkmp.domain.model.UserDto

@Composable
fun ConversationListItem(
    conversation: ConversationDto,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = conversation.user.fullName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        },
        supportingContent = {
            conversation.lastMessage?.text?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = {
            UserAvatar(photoUrl = conversation.user.photo?.url)
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
            channel = ChannelDto(id = "c1", kind = "telegram", name = "Telegram"),
            state = StateDto(
                stoppedByManager = false,
                operatorTagged = false,
                hasUnansweredOperatorMessage = true
            ),
            lastMessage = MessageDto(
                id = "m1",
                text = "Напиши, пожалуйста, имя и фамилию...",
                dateCreated = "2024-03-09T15:12:00"
            )
        )
    )
}