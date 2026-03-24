package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AvatarWithChannel
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatDateDivider
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatInputField
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatMessageItem
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.PendingAttachmentsRow
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

@Composable
fun ChatScreen(
    conversationId: Long,
    userId: String,
    onNavigateBack: () -> Unit,
    onUserInfo: () -> Unit,
    viewModel: ChatViewModel = viewModel { ChatViewModel() },
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatContent(
        messages = state.messages,
        pendingAttachments = state.pendingAttachments,
        messageInput = viewModel.messageInputState,
        onBack = onNavigateBack,
        onUserInfo = onUserInfo,
        onToggleBot = {},
        onAttach = {},
        onEmoji = {},
        onSend = {},
        onRemoveAttachment = {}
    )
}

@Composable
fun ChatContent(
    messages: List<ChatListItemUi>,
    pendingAttachments: List<ChatAttachmentUi>,
    messageInput: TextFieldState,
    onBack: () -> Unit,
    onUserInfo: () -> Unit,
    onToggleBot: () -> Unit,
    onAttach: () -> Unit,
    onEmoji: () -> Unit,
    onSend: () -> Unit,
    onRemoveAttachment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
        topBar = {
            ChatTopBar(
                onBack = onBack,
                onToggleBot = onToggleBot,
                onUserInfo = onUserInfo
            )
        },
        bottomBar = {
            ChatBottomBar(
                messageInput = messageInput,
                pendingAttachments = pendingAttachments,
                onAttach = onAttach,
                onEmoji = onEmoji,
                onSend = onSend,
                onRemoveAttachment = onRemoveAttachment
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MessageList(items = messages)
        }
    }
}

@Composable
private fun MessageList(
    items: List<ChatListItemUi>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = Dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall),
    ) {
        items(items = items, key = { it.id }) { item ->
            when (item) {
                is ChatListItemUi.Message -> ChatMessageItem(message = item.data)
                is ChatListItemUi.DateHeader -> ChatDateDivider(text = item.text)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatTopBar(
    onBack: () -> Unit,
    onToggleBot: () -> Unit,
    onUserInfo: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarWithChannel(
                    name = "Имя",
                    photoUrl = "",
                    channelKind = ChannelKind.TG,
                )

                Spacer(Modifier.width(Dimensions.spacingSmall))
                Text(
                    text = "Имя",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        actions = {
            IconButton(
                onClick = onToggleBot,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(Dimensions.socialButtonIconSize)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(Dimensions.filterButtonIconSize),
                )

            }
            IconButton(onClick = onUserInfo) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

@Composable
private fun ChatBottomBar(
    messageInput: TextFieldState,
    pendingAttachments: List<ChatAttachmentUi>,
    onAttach: () -> Unit,
    onEmoji: () -> Unit,
    onSend: () -> Unit,
    onRemoveAttachment: (String) -> Unit,
) {
    HorizontalDivider()
    Column {
        if (pendingAttachments.isNotEmpty()) {
            PendingAttachmentsRow(
                items = pendingAttachments,
                onRemove = onRemoveAttachment
            )
        }
        ChatInputField(
            state = messageInput,
            onAttach = onAttach,
            onEmoji = onEmoji,
            onSend = onSend,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChatTopBarPreview() {
    AppTheme {
        ChatTopBar(
            onBack = {},
            onToggleBot = {},
            onUserInfo = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBottomBarEmptyPreview() {
    AppTheme {
        ChatBottomBar(
            messageInput = TextFieldState(),
            pendingAttachments = emptyList(),
            onAttach = {},
            onEmoji = {},
            onSend = {},
            onRemoveAttachment = {}
        )
    }
}