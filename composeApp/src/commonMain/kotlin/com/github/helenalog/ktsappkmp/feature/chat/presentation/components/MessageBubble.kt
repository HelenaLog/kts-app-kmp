package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.UserAvatar
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SocialButtonBorder
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatMessageUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import kotlinx.datetime.Instant
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_ic_bot_avatar
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatBubbleContainer(
    containerColor: Color,
    borderColor: Color,
    bottomStartRadius: Dp,
    bottomEndRadius: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        shape = RoundedCornerShape(
            topStart = Dimensions.bubbleCornerRadius,
            topEnd = Dimensions.bubbleCornerRadius,
            bottomStart = bottomStartRadius,
            bottomEnd = bottomEndRadius
        ),
        border = BorderStroke(Dimensions.socialButtonBorderWidth, borderColor)
    ) {
        Column(modifier = Modifier.padding(Dimensions.spacingSmall)) {
            content()
        }
    }
}

@Composable
fun MessageTime(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier.padding(bottom = Dimensions.timeBottomPadding)
    )
}

@Composable
fun OutgoingMessageBubble(
    message: ChatMessageUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall, Alignment.End),
        verticalAlignment = Alignment.Bottom
    ) {
        MessageTime(text = message.formattedTime)
        ChatBubbleContainer(
            modifier = Modifier.weight(1f, fill = false),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            borderColor = SocialButtonBorder,
            bottomStartRadius = Dimensions.bubbleCornerRadius,
            bottomEndRadius = Dimensions.bubbleSmallRadius
        ) {
            AttachmentsList(message.attachments)
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Image(
            painter = painterResource(Res.drawable.chat_ic_bot_avatar),
            contentDescription = null,
            modifier = Modifier
                .size(Dimensions.avatarSize)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun IncomingMessageBubble(
    message: ChatMessageUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall, Alignment.Start),
        verticalAlignment = Alignment.Bottom
    ) {
        UserAvatar(
            avatar = message.avatar,
        )
        ChatBubbleContainer(
            modifier = Modifier.weight(1f, fill = false),
            containerColor = MaterialTheme.colorScheme.primary,
            borderColor = Color.Transparent,
            bottomStartRadius = Dimensions.bubbleSmallRadius,
            bottomEndRadius = Dimensions.bubbleCornerRadius
        ) {
            AttachmentsList(message.attachments)
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
        MessageTime(text = message.formattedTime)
    }
}

@Composable
fun AttachmentsList(
    items: List<ChatAttachmentUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
    ) {
        items.forEach { attachment ->
            key(attachment.id) {
                MessageAttachmentItem(attachment)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomingMessageBubblePreview() {
    AppTheme {
        IncomingMessageBubble(
            ChatMessageUi(
                id = "1",
                text = "Текст",
                formattedTime = "10:10",
                date = "2026-03-28",
                isOutgoing = true,
                kind = MessageKind.USER,
                userName = "Иван",
                userPhotoUrl = "",
                attachments = emptyList(),
                avatar = UserAvatarUi("?", ""),
                createdAt = Instant.DISTANT_PAST
            )
        )
    }
}
