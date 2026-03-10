package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.MessageKind
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.main_message_kind_bot
import ktsappkmp.composeapp.generated.resources.main_message_kind_manager
import ktsappkmp.composeapp.generated.resources.main_message_kind_service
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessagePreview(
    kind: MessageKind?,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (kind) {
            MessageKind.BOT -> MessageKindLabel(
                icon = Icons.Default.SmartToy,
                label = stringResource(Res.string.main_message_kind_bot),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            MessageKind.SERVICE -> MessageKindLabel(
                icon = Icons.Default.Settings,
                label = stringResource(Res.string.main_message_kind_service),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            MessageKind.MANAGER -> MessageKindLabel(
                icon = Icons.Default.Person,
                label = stringResource(Res.string.main_message_kind_manager),
                tint = MaterialTheme.colorScheme.primary,
            )
            MessageKind.USER -> Unit
            null -> Unit
        }

        if (text.isNotEmpty()) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
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