package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AvatarWithChannel
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.EmptyContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.ErrorContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatDateDivider
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatInputField
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatMessageItem
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.PendingAttachmentsRow
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ScrollToBottomButton
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_ic_user_info
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
    conversationId: Long,
    userId: String,
    onNavigateBack: () -> Unit,
    onUserInfo: () -> Unit,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val filePickerLauncher = rememberFilePickerLauncher { file ->
        file?.let { viewModel.onFileSelected(it) }
    }

    LaunchedEffect(conversationId) {
        viewModel.loadScreen(conversationId, userId)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ChatUiEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    ChatContent(
        messages = state.messages,
        pendingAttachments = state.pendingAttachments,
        messageInput = viewModel.messageInputState,
        userName = state.userName,
        avatar = state.avatar,
        channelKind = state.channelKind,
        isLoading = state.isLoading,
        error = state.error,
        onBack = onNavigateBack,
        onUserInfo = onUserInfo,
        onToggleBot = {},
        onAttach = { filePickerLauncher.launch() },
        onEmoji = {},
        onSend = { viewModel.sendMessage(conversationId) },
        onRemoveAttachment = { viewModel.removeAttachment(it) },
        onRetry = { viewModel.loadScreen(conversationId, userId) },
        botName = state.botName
    )
}

@Composable
fun ChatContent(
    messages: List<ChatListItemUi>,
    pendingAttachments: List<ChatAttachmentUi>,
    messageInput: TextFieldState,
    userName: String,
    avatar: UserAvatarUi,
    botName: String,
    channelKind: ChannelKind,
    isLoading: Boolean,
    error: String?,
    onBack: () -> Unit,
    onUserInfo: () -> Unit,
    onToggleBot: () -> Unit,
    onAttach: () -> Unit,
    onEmoji: () -> Unit,
    onSend: () -> Unit,
    onRemoveAttachment: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
        topBar = {
            ChatTopBar(
                channelKind = channelKind,
                avatar = avatar,
                userName = userName,
                onBack = onBack,
                botName = botName,
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
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> ErrorContent(
                    message = error,
                    onRetry = onRetry
                )

                messages.isEmpty() -> EmptyContent()
                else -> MessageList(items = messages)
            }
        }
    }
}

@Composable
private fun MessageList(
    items: List<ChatListItemUi>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(items.size) {
        if (items.isNotEmpty()) {
            listState.animateScrollToItem(items.size - 1)
        }
    }

    val showButton by remember {
        derivedStateOf {
            listState.canScrollForward
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(
                top = Dimensions.spacingMedium,
                bottom = Dimensions.spacingMedium
            ),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall),
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(horizontal = Dimensions.spacingMedium),
        ) {
            items(items = items, key = { it.id }) { item ->
                when (item) {
                    is ChatListItemUi.Message -> ChatMessageItem(message = item.data)
                    is ChatListItemUi.DateHeader -> ChatDateDivider(text = item.text)
                }
            }
        }

        ScrollToBottomButton(
            visible = showButton,
            onClick = {
                scope.launch {
                    if (items.isNotEmpty()) {
                        listState.animateScrollToItem(items.size - 1)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimensions.spacingMedium)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatTopBar(
    userName: String = "",
    avatar: UserAvatarUi,
    botName: String,
    channelKind: ChannelKind = ChannelKind.TG,
    onBack: () -> Unit,
    onToggleBot: () -> Unit,
    onUserInfo: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarWithChannel(
                    channelKind = channelKind,
                    avatar = avatar
                )
                Spacer(Modifier.width(Dimensions.spacingSmall))
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = botName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
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
                    painter = painterResource(Res.drawable.chat_ic_user_info),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
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
    Column(
        modifier = Modifier.navigationBarsPadding()
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ChatTopBarPreview() {
    AppTheme {
        ChatTopBar(
            onBack = {},
            onToggleBot = {},
            onUserInfo = {},
            userName = "Имя",
            avatar = UserAvatarUi("?", photoUrl = ""),
            channelKind = ChannelKind.TG,
            botName = "имя бота"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBottomBarEmptyPreview() {
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