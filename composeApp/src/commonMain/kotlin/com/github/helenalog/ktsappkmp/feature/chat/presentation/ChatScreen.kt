package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatDateDivider
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatInputField
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatMessageItem
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.PendingAttachmentsRow

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel { ChatViewModel() },
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            MessageList(
                items = state.messages,
            )
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