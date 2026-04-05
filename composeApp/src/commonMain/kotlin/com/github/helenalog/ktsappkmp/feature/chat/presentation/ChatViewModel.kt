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
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.ObserveWsMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StartBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StopBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.UploadAttachmentUseCase
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
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
    private val mapper: ChatUiMapper,
    private val avatarUiMapper: UserAvatarUiMapper
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
                            channelId = detail.channelId
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
            .map { mapper.toDomain(it) }

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
                    mapper.mapMessages(
                        newMessages,
                        currentState.userName,
                        currentState.userPhotoUrl
                    )
                }
                val allItems = withContext(Dispatchers.Default) {
                    mapper.addDateHeaders(existing + newMapped)
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

    private fun uploadAttachment(bytes: ByteArray, fileName: String, mimeType: String) {
        viewModelScope.launch {
            updateState { copy(attachmentState = AttachmentState.Loading) }
            uploadAttachmentUseCase(fileName, bytes, mimeType)
                .onSuccess { attachment ->
                    val uiModel = mapper.mapAttachment(attachment)
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
                    mapper.addDateHeaders(
                        mapper.mapMessages(
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
            data = mapper.mapMessage(
                domain = message,
                userName = currentState.userName,
                userPhotoUrl = currentState.userPhotoUrl
            )
        )
        val existing = currentState.messages.filterIsInstance<ChatListItemUi.Message>()
        val allItems = mapper.addDateHeaders(existing + newItem)
        updateState { copy(messages = allItems) }
    }

    private companion object {
        const val UNKNOWN_ERROR = "Неизвестная ошибка"
        const val EMPTY_FILE_ERROR = "Файл пуст"
        const val FILE_READ_ERROR = "Не удалось прочитать файл"
        const val WS_ERROR = "Ошибка соединения"
        const val PAGE_SIZE = 20
    }
}
