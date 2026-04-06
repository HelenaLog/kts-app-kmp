package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.core.presentation.common.PaginationState
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

@Immutable
data class ChatUiState(
    val messages: List<ChatListItemUi> = emptyList(),
    val pendingAttachments: List<ChatAttachmentUi> = emptyList(),
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val avatar: UserAvatarUi = UserAvatarUi("?", photoUrl = ""),
    val botName: String = "",
    val channelPhoto: String? = null,
    val channelKind: ChannelKind = ChannelKind.UNKNOWN,
    val userId: String = "",
    val channelId: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val pagination: PaginationState = PaginationState(),
    val attachmentState: AttachmentState = AttachmentState.Idle
)

sealed interface AttachmentState {
    data object Idle : AttachmentState
    data object Loading : AttachmentState
    data class Error(val message: String) : AttachmentState
}

