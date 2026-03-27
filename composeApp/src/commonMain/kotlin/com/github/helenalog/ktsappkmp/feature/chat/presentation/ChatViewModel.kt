package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ChatUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetConversationDetailUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val getDetailUseCase: GetConversationDetailUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val mapper: ChatUiMapper,
    private val avatarUiMapper: UserAvatarUiMapper,
) : BaseViewModel<ChatUiState, ChatUiEvent>(initialState = ChatUiState()) {
    val messageInputState = TextFieldState()

    fun loadScreen(conversationId: Long, userId: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            getDetailUseCase(conversationId, userId)
                .onSuccess { detail ->
                    updateState {
                        copy(
                            userName = detail.userName,
                            userPhotoUrl = detail.photoUrl,
                            botName = detail.botName,
                            channelKind = detail.channelKind,
                            avatar = avatarUiMapper.map(detail.userName, detail.photoUrl),
                            userId = detail.userId,
                            channelId = detail.channelId
                        )
                    }
                    loadMessages(conversationId)
                }
                .onFailure { e ->
                    updateState {
                        copy(error = e.message ?: UNKNOWN_ERROR)
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
                    mapper.mapToList(messages, state.userName, state.userPhotoUrl)
                }
                updateState { copy(messages = items, isLoading = false) }
            }
            .onFailure { e ->
                updateState { copy(error = e.message ?: UNKNOWN_ERROR, isLoading = false) }
            }
    }

    fun sendMessage(conversationId: Long) {
        val text = messageInputState.text.toString().trim()
        val attachments = state.value.pendingAttachments
            .map { mapper.mapAttachmentToDomain(it) }
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            sendMessageUseCase(conversationId, text, attachments)
                .onSuccess {
                    messageInputState.clearText()
                    loadMessages(conversationId)
                }
                .onFailure { e ->
                    updateState { copy(error = e.message ?: UNKNOWN_ERROR, isLoading = false) }
                }
        }
    }

    private companion object {
        const val UNKNOWN_ERROR = "Неизвестная ошибка"
    }
}