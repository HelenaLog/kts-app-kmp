package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AvatarWithChannel
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.EmptyContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.ErrorContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.BotActionBottomSheet
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.BotActionButton
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatDateDivider
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatInputField
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ChatMessageItem
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.PendingAttachmentsRow
import com.github.helenalog.ktsappkmp.feature.chat.presentation.components.ScrollToBottomButton
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatBottomBarState
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListState
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatTopBarState
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_empty_messages
import ktsappkmp.composeapp.generated.resources.chat_ic_user_info
import ktsappkmp.composeapp.generated.resources.ic_bot_preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
    conversationId: Long,
    userId: String,
    onNavigateBack: () -> Unit,
    onUserInfo: () -> Unit,
    viewModel: ChatViewModel = koinViewModel()
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
        topBarState = state.topBarState,
        listState = state.listState,
        bottomBarState = state.bottomBarState,
        botActionState = state.botActionState,
        handlers = remember(conversationId, userId) {
            ChatHandlers(
                navigation = NavigationHandlers(
                    onBack = onNavigateBack,
                    onUserInfo = onUserInfo,
                    onRetry = { viewModel.loadScreen(conversationId, userId) }
                ),
                message = MessageHandlers(
                    onSend = { viewModel.sendMessage(conversationId) },
                    onAttach = { filePickerLauncher.launch() },
                    onLoadMore = { viewModel.loadMoreMessages(conversationId) }
                ),
                attachment = AttachmentHandlers(
                    onRemoveAttachment = { viewModel.removeAttachment(it) },
                    onDismissAttachmentError = { viewModel.dismissAttachmentError() }
                ),
                botAction = BotActionHandlers(
                    onStopBot = { viewModel.stopBot(conversationId) },
                    onStartBot = { viewModel.startBot(conversationId) },
                    onOpenBotActionSheet = { viewModel.openBotActionSheet() },
                    onDismissBotAction = { viewModel.dismissBotAction() },
                    onSelectScenario = { viewModel.selectScenario(it) },
                    onRunScenario = { blockId -> viewModel.runScenario(conversationId, blockId) },
                    onBackToScenarios = { viewModel.backToScenarioList() },
                    onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) }
                ),
            )
        },
        messageInput = viewModel.messageInputState
    )
}

@Composable
fun ChatContent(
    topBarState: ChatTopBarState,
    listState: ChatListState,
    bottomBarState: ChatBottomBarState,
    botActionState: BotActionState,
    handlers: ChatHandlers,
    messageInput: TextFieldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
        topBar = {
            ChatTopBar(
                state = topBarState,
                navigationHandlers = handlers.navigation,
                botActionHandlers = handlers.botAction
            )
        },
        bottomBar = {
            ChatBottomBar(
                state = bottomBarState,
                messageInput = messageInput,
                messageHandlers = handlers.message,
                attachmentHandlers = handlers.attachment
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                listState.isLoading -> CircularProgressIndicator()
                listState.error != null -> ErrorContent(
                    message = listState.error,
                    onRetry = handlers.navigation.onRetry
                )

                listState.messages.isEmpty() -> EmptyContent(
                    message = stringResource(Res.string.chat_empty_messages)
                )

                else -> MessageList(
                    items = listState.messages,
                    isLoadingMore = listState.isLoadingMore,
                    hasMore = listState.hasMore,
                    onLoadMore = handlers.message.onLoadMore
                )
            }
        }
    }

    when (botActionState) {
        is BotActionState.Loading,
        is BotActionState.ScenarioPickerOpen,
        is BotActionState.BlockPickerOpen,
        is BotActionState.Error -> BotActionBottomSheet(
            state = botActionState,
            handlers = handlers.botAction
        )

        else -> Unit
    }
}

@Composable
private fun MessageList(
    items: List<ChatListItemUi>,
    isLoadingMore: Boolean,
    hasMore: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val reachedTop by remember {
        derivedStateOf {
            !listState.canScrollForward && hasMore && !isLoadingMore
        }
    }

    LaunchedEffect(items.size) {
        if (listState.firstVisibleItemIndex <= 2) {
            listState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(reachedTop) {
        if (reachedTop) {
            onLoadMore()
        }
    }

    val showButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 2 }
    }

    Box(
        modifier = modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        LazyColumn(
            state = listState,
            reverseLayout = true,
            contentPadding = PaddingValues(
                top = Dimensions.spacingMedium,
                bottom = Dimensions.spacingMedium
            ),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium),
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(horizontal = Dimensions.spacingMedium)
        ) {
            items(
                items = items,
                key = { item ->
                    when (item) {
                        is ChatListItemUi.Message -> "msg_${item.data.id}"
                        is ChatListItemUi.DateHeader -> "date_${item.dateKey}"
                    }
                }
            ) { item ->
                when (item) {
                    is ChatListItemUi.Message -> ChatMessageItem(message = item.data)
                    is ChatListItemUi.DateHeader -> ChatDateDivider(text = item.text)
                }
            }

            if (isLoadingMore) {
                item(key = "loading_more") {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(Dimensions.spacingSmall),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        ScrollToBottomButton(
            visible = showButton,
            onClick = {
                scope.launch { listState.animateScrollToItem(0) }
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
    state: ChatTopBarState,
    navigationHandlers: NavigationHandlers,
    botActionHandlers: BotActionHandlers,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = navigationHandlers.onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    AvatarWithChannel(channelKind = state.channelKind, avatar = state.avatar)
                    Spacer(Modifier.width(Dimensions.spacingSmall))
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = state.userName,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = state.channelPhoto,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                error = painterResource(Res.drawable.ic_bot_preview),
                                placeholder = painterResource(Res.drawable.ic_bot_preview),
                                modifier = Modifier.size(Dimensions.botIconSize)
                            )
                            Spacer(Modifier.width(Dimensions.spacingSmall))
                            Text(
                                text = state.botName,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            },
            actions = {
                BotActionButton(
                    isBotActive = state.isBotActive,
                    onStopBot = botActionHandlers.onStopBot,
                    onStartBot = botActionHandlers.onStartBot,
                    onOpenBotActionSheet = botActionHandlers.onOpenBotActionSheet
                )
                IconButton(onClick = navigationHandlers.onUserInfo) {
                    Icon(
                        painter = painterResource(Res.drawable.chat_ic_user_info),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            },
        )
        HorizontalDivider()
    }
}

@Composable
private fun ChatBottomBar(
    state: ChatBottomBarState,
    messageInput: TextFieldState,
    messageHandlers: MessageHandlers,
    attachmentHandlers: AttachmentHandlers,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.navigationBarsPadding()) {
        HorizontalDivider()
        when (state.attachmentState) {
            is AttachmentState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth())
            is AttachmentState.Error -> Text(
                text = state.attachmentState.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.spacingMedium)
                    .clickable { attachmentHandlers.onDismissAttachmentError() }
            )

            is AttachmentState.Idle -> Unit
        }
        if (state.pendingAttachments.isNotEmpty()) {
            PendingAttachmentsRow(
                items = state.pendingAttachments,
                onRemove = attachmentHandlers.onRemoveAttachment
            )
        }
        ChatInputField(
            state = messageInput,
            onAttach = messageHandlers.onAttach,
            onSend = messageHandlers.onSend,
            hasAttachments = state.pendingAttachments.isNotEmpty()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ChatTopBarPreview() {
    AppTheme {
        ChatTopBar(
            state = ChatTopBarState(
                userName = "Имя",
                avatar = UserAvatarUi("?", photoUrl = ""),
                channelKind = ChannelKind.TG,
                botName = "имя бота",
                channelPhoto = "",
                isBotActive = true
            ),
            navigationHandlers = NavigationHandlers(
                onBack = {},
                onUserInfo = {},
                onRetry = {}
            ),
            botActionHandlers = BotActionHandlers(
                onStopBot = {},
                onStartBot = {},
                onOpenBotActionSheet = {},
                onDismissBotAction = {},
                onSelectScenario = {},
                onRunScenario = {},
                onBackToScenarios = {},
                onSearchQueryChanged = {}
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBottomBarEmptyPreview() {
    AppTheme {
        ChatBottomBar(
            state = ChatBottomBarState(
                pendingAttachments = emptyList(),
                attachmentState = AttachmentState.Idle
            ),
            messageInput = TextFieldState(),
            messageHandlers = MessageHandlers(
                onSend = {},
                onAttach = {},
                onLoadMore = {}
            ),
            attachmentHandlers = AttachmentHandlers(
                onRemoveAttachment = {},
                onDismissAttachmentError = {}
            )
        )
    }
}
