package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_ic_user_info
import ktsappkmp.composeapp.generated.resources.ic_attachment
import ktsappkmp.composeapp.generated.resources.ic_bot_preview
import ktsappkmp.composeapp.generated.resources.main_message_kind_bot
import ktsappkmp.composeapp.generated.resources.main_message_kind_manager
import ktsappkmp.composeapp.generated.resources.message_preview_attachments
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessagePreview(
    kind: MessageKind?,
    text: String,
    isUnread: Boolean = false,
    attachmentCount: Int = 0,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        when (kind) {
            MessageKind.BOT -> MessageKindLabel(
                icon = painterResource(Res.drawable.ic_bot_preview),
                label = stringResource(Res.string.main_message_kind_bot),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            MessageKind.SERVICE -> ServiceMessagePreview(text = text)
            MessageKind.MANAGER -> MessageKindLabel(
                icon = painterResource(Res.drawable.chat_ic_user_info),
                label = stringResource(Res.string.main_message_kind_manager),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            MessageKind.USER -> Unit
            null -> Unit
        }

        if (attachmentCount > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                MessageKindLabel(
                    icon = painterResource(Res.drawable.ic_attachment),
                    label = pluralStringResource(
                        Res.plurals.message_preview_attachments,
                        attachmentCount,
                        attachmentCount
                    ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                if (isUnread && text.isEmpty()) {
                    UnreadDot()
                }
            }
        }

        if (kind != MessageKind.SERVICE && text.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                if (isUnread) {
                    UnreadDot()
                }
            }
        }
    }
}

@Composable
private fun UnreadDot(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = Dimensions.spacingSmall)
            .size(Dimensions.messageUnreadIndicatorSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
private fun ServiceMessagePreview(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingExtraSmall)
    ) {
        Box(
            modifier = Modifier
                .size(Dimensions.messageServiceDotSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun MessagePreviewBot() {
    MessagePreview(
        kind = MessageKind.BOT,
        text = "Напиши, пожалуйста, имя и фамилию...",
    )
}

@Preview
@Composable
private fun MessagePreviewUser() {
    MessagePreview(
        kind = MessageKind.USER,
        text = "Привет, как дела?",
    )
}
