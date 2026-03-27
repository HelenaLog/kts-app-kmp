package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatMessageUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

@Composable
fun ChatMessageItem(
    message: ChatMessageUi,
    modifier: Modifier = Modifier
) {
    when (message.kind) {
        MessageKind.SERVICE -> {
            SystemMessage(text = message.text, modifier = modifier)
        }

        MessageKind.USER -> {
            IncomingMessageBubble(
                message = message,
                modifier = modifier
            )
        }

        MessageKind.BOT, MessageKind.MANAGER -> {
            OutgoingMessageBubble(
                message = message,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessageItemPreview() {
    AppTheme {
        ChatMessageItem(
            ChatMessageUi(
                id = "1",
                text = "Текст",
                formattedTime = "10:10",
                isOutgoing = false,
                kind = MessageKind.BOT,
                userName = "Имя",
                userPhotoUrl = "",
                attachments = emptyList(),
                avatar = UserAvatarUi("?", ""),
            )
        )
    }
}