package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.WebSocketEvent
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ChatUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetConversationDetailUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetScenarioBlocksUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetScenariosUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.ObserveWsMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.RunScenarioUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StartBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StopBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.UploadAttachmentUseCase
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.BlockUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ScenarioUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi
import io.github.vinceglb.filekit.core.PlatformFile
import io.github.vinceglb.filekit.core.extension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val getDetailUseCase: GetConversationDetailUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val observeMessagesUseCase: ObserveWsMessagesUseCase,
    private val uploadAttachmentUseCase: UploadAttachmentUseCase,
    private val startBotUseCase: StartBotUseCase,
    private val stopBotUseCase: StopBotUseCase,
    private val getScenariosUseCase: GetScenariosUseCase,
    private val getScenarioBlocksUseCase: GetScenarioBlocksUseCase,
    private val runScenarioUseCase: RunScenarioUseCase,
    private val chatUiMapper: ChatUiMapper,
    private val avatarUiMapper: UserAvatarUiMapper,
    private val scenarioUiMapper: ScenarioUiMapper,
    private val blockUiMapper: BlockUiMapper,
) : BaseViewModel<ChatUiState, ChatUiEvent>(initialState = ChatUiState()) {
    val messageInputState = TextFieldState()
    private var wsJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        wsJob?.cancel()
    }

    fun loadScreen(conversationId: Long, userId: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            getDetailUseCase(conversationId, userId)
                .onSuccess { detail ->
                    val userAvatar = avatarUiMapper.map(detail.userName, detail.photoUrl)
                    updateState {
                        copy(
                            userName = detail.userName,
                            userPhotoUrl = detail.photoUrl,
                            botName = detail.botName,
                            channelPhoto = detail.channelPhoto,
                            channelKind = detail.channelKind,
                            avatar = userAvatar,
                            userId = detail.userId,
                            channelId = detail.channelId,
                            isBotActive = !detail.stoppedByManager
                        )
                    }
                    loadMessages(conversationId)
                    startWebSocket(conversationId)
                }
                .onFailure { e ->
                    updateState {
                        copy(error = e.message ?: UNKNOWN_ERROR)
                    }
                }
        }
    }

    fun sendMessage(conversationId: Long) {
        val text = messageInputState.text.toString()
        val attachments = state.value.pendingAttachments
            .map { chatUiMapper.toDomain(it) }

        viewModelScope.launch {
            sendMessageUseCase(conversationId, text, attachments)
                .onSuccess {
                    messageInputState.clearText()
                    updateState { copy(pendingAttachments = emptyList()) }
                }
                .onFailure { e ->
                    updateState { copy(error = e.message ?: UNKNOWN_ERROR) }
                }
        }
    }

    fun onFileSelected(file: PlatformFile) {
        viewModelScope.launch {
            updateState { copy(attachmentState = AttachmentState.Loading) }
            val result = withContext(Dispatchers.IO) {
                suspendRunCatching {
                    val bytes = file.readBytes()
                    if (bytes.isEmpty()) throw IllegalStateException(EMPTY_FILE_ERROR)
                    bytes
                }
            }
            result.onSuccess { bytes ->
                uploadAttachment(bytes, file.name, file.extension)
            }.onFailure { e ->
                updateState {
                    copy(attachmentState = AttachmentState.Error(e.message ?: FILE_READ_ERROR))
                }
            }
        }
    }

    fun removeAttachment(id: String) =
        updateState { copy(pendingAttachments = pendingAttachments.filter { it.id != id }) }

    fun dismissAttachmentError() = updateState { copy(attachmentState = AttachmentState.Idle) }

    fun loadMoreMessages(conversationId: Long) {
        val currentState = state.value
        if (currentState.pagination.isLoading || !currentState.pagination.hasMore) return

        viewModelScope.launch {
            updateState { copy(pagination = pagination.copy(isLoading = true)) }
            getMessagesUseCase(
                conversationId = conversationId,
                userId = currentState.userId,
                channelId = currentState.channelId,
                fromId = currentState.pagination.cursor
            ).onSuccess { newMessages ->
                val existing = currentState.messages.filterIsInstance<ChatListItemUi.Message>()
                val newMapped = withContext(Dispatchers.Default) {
                    chatUiMapper.mapMessages(
                        newMessages,
                        currentState.userName,
                        currentState.userPhotoUrl
                    )
                }
                val allItems = withContext(Dispatchers.Default) {
                    chatUiMapper.addDateHeaders(existing + newMapped)
                }
                updateState {
                    copy(
                        messages = allItems,
                        pagination = pagination.copy(
                            isLoading = false,
                            hasMore = newMessages.size >= PAGE_SIZE,
                            cursor = allItems
                                .filterIsInstance<ChatListItemUi.Message>()
                                .lastOrNull()?.data?.id
                        )
                    )
                }
            }.onFailure { e ->
                updateState {
                    copy(
                        pagination = pagination.copy(
                            isLoading = false,
                            error = e.message ?: UNKNOWN_ERROR
                        )
                    )
                }
            }
        }
    }

    fun startBot(conversationId: Long) {
        viewModelScope.launch {
            startBotUseCase(conversationId, state.value.userId)
                .onSuccess {
                    updateState { copy(isBotActive = true, botActionState = BotActionState.Idle) }
                }
                .onFailure { e ->
                    updateState { copy(error = e.message ?: UNKNOWN_ERROR) }
                }
        }
    }

    fun stopBot(conversationId: Long) {
        viewModelScope.launch {
            stopBotUseCase(conversationId, state.value.userId)
                .onSuccess {
                    updateState { copy(isBotActive = false, botActionState = BotActionState.Idle) }
                }
                .onFailure { e ->
                    updateState { copy(error = e.message ?: UNKNOWN_ERROR) }
                }
        }
    }

    fun openBotActionSheet() = launchBotAction(
        action = { getScenariosUseCase() },
        onSuccess = { scenarios ->
            val mapped = scenarioUiMapper.mapList(scenarios)
            updateState {
                copy(
                    scenarios = mapped,
                    botActionState = BotActionState.ScenarioPickerOpen(scenarios = mapped)
                )
            }
        }
    )

    fun selectScenario(scenario: ScenarioUi) = launchBotAction(
        action = { getScenarioBlocksUseCase(scenario.id) },
        onSuccess = { blocks ->
            updateState {
                copy(
                    botActionState = BotActionState.BlockPickerOpen(
                        scenario = scenario,
                        blocks = blockUiMapper.mapList(blocks)
                    )
                )
            }
        }
    )

    fun runScenario(conversationId: Long, blockId: String) = launchBotAction(
        action = { runScenarioUseCase(conversationId, blockId) },
        onSuccess = {
            updateState { copy(isBotActive = true, botActionState = BotActionState.Idle) }
        }
    )

    fun dismissBotAction() {
        updateState { copy(botActionState = BotActionState.Idle) }
    }

    fun backToScenarioList() {
        updateState {
            copy(botActionState = BotActionState.ScenarioPickerOpen(scenarios))
        }
    }

    fun onSearchQueryChanged(query: String) {
        val currentState = state.value.botActionState
        when (currentState) {
            is BotActionState.ScenarioPickerOpen -> updateState {
                copy(
                    botActionState = currentState.copy(
                        query = query,
                        filteredScenarios = currentState.scenarios.filterByQuery(query) { it.name }
                    )
                )
            }
            is BotActionState.BlockPickerOpen -> updateState {
                copy(
                    botActionState = currentState.copy(
                        query = query,
                        filteredBlocks = currentState.blocks.filterByQuery(query) { it.name }
                    )
                )
            }
            else -> Unit
        }
    }

    private fun uploadAttachment(bytes: ByteArray, fileName: String, mimeType: String) {
        viewModelScope.launch {
            updateState { copy(attachmentState = AttachmentState.Loading) }
            uploadAttachmentUseCase(fileName, bytes, mimeType)
                .onSuccess { attachment ->
                    val uiModel = chatUiMapper.mapAttachment(attachment)
                    updateState {
                        copy(
                            attachmentState = AttachmentState.Idle,
                            pendingAttachments = pendingAttachments + uiModel
                        )
                    }
                }
                .onFailure { e ->
                    updateState {
                        copy(attachmentState = AttachmentState.Error(e.message ?: UNKNOWN_ERROR))
                    }
                }
        }
    }

    private suspend fun loadMessages(conversationId: Long) {
        val state = state.value
        updateState { copy(isLoading = true, error = null) }
        getMessagesUseCase(conversationId, state.userId, state.channelId)
            .onSuccess { messages ->
                val items = withContext(Dispatchers.Default) {
                    chatUiMapper.addDateHeaders(
                        chatUiMapper.mapMessages(
                            messages,
                            state.userName,
                            state.userPhotoUrl
                        )
                    )
                }
                updateState {
                    copy(
                        messages = items,
                        isLoading = false,
                        pagination = pagination.copy(
                            hasMore = messages.size >= PAGE_SIZE,
                            cursor = items
                                .filterIsInstance<ChatListItemUi.Message>()
                                .lastOrNull()?.data?.id
                        )
                    )
                }
            }.onFailure { e ->
                updateState { copy(error = e.message ?: UNKNOWN_ERROR, isLoading = false) }
            }
    }

    private fun startWebSocket(conversationId: Long) {
        wsJob?.cancel()
        wsJob = viewModelScope.launch {
            observeMessagesUseCase(conversationId).collect { event ->
                handleWsEvent(event)
            }
        }
    }

    private fun handleWsEvent(event: WebSocketEvent) {
        when (event) {
            is WebSocketEvent.NewMessage -> appendMessage(event.message)
            is WebSocketEvent.Error -> updateState { copy(error = WS_ERROR) }
            is WebSocketEvent.Connected,
            is WebSocketEvent.Reconnecting -> Unit
        }
    }

    private fun appendMessage(message: ChatMessage) {
        val currentState = state.value
        if (currentState.messages.any { messageItem ->
                messageItem is ChatListItemUi.Message && messageItem.data.id == message.id
            }
        ) return
        val newItem = ChatListItemUi.Message(
            data = chatUiMapper.mapMessage(
                domain = message,
                userName = currentState.userName,
                userPhotoUrl = currentState.userPhotoUrl
            )
        )
        val existing = currentState.messages.filterIsInstance<ChatListItemUi.Message>()
        val allItems = chatUiMapper.addDateHeaders(existing + newItem)
        updateState { copy(messages = allItems) }
    }

    private fun <T> List<T>.filterByQuery(query: String, selector: (T) -> String): List<T> =
        if (query.isBlank()) this
        else filter { selector(it).contains(query, ignoreCase = true) }

    private fun <T> launchBotAction(
        action: suspend () -> Result<T>,
        onSuccess: (T) -> Unit,
    ) {
        viewModelScope.launch {
            updateState { copy(botActionState = BotActionState.Loading) }
            action()
                .onSuccess(onSuccess)
                .onFailure { e ->
                    updateState {
                        copy(botActionState = BotActionState.Error(e.message ?: UNKNOWN_ERROR))
                    }
                }
        }
    }

    private companion object {
        const val UNKNOWN_ERROR = "Неизвестная ошибка"
        const val EMPTY_FILE_ERROR = "Файл пуст"
        const val FILE_READ_ERROR = "Не удалось прочитать файл"
        const val WS_ERROR = "Ошибка соединения"
        const val PAGE_SIZE = 20
    }
}
