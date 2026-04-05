package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.core.presentation.common.PaginationState
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatBottomBarState
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListState
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatTopBarState
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

@Immutable
data class ChatUiState(
    val messages: List<ChatListItemUi> = emptyList(),
    val pendingAttachments: List<ChatAttachmentUi> = emptyList(),
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val avatar: UserAvatarUi = UserAvatarUi("?", photoUrl = ""),
    val botName: String = "",
    val channelPhoto: String = "",
    val channelKind: ChannelKind = ChannelKind.UNKNOWN,
    val userId: String = "",
    val channelId: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val pagination: PaginationState = PaginationState(),
    val attachmentState: AttachmentState = AttachmentState.Idle,
    val isBotActive: Boolean = true,
    val botActionState: BotActionState = BotActionState.Idle,
    val scenarios: List<ScenarioUi> = emptyList()
) {
    val topBarState: ChatTopBarState
        get() = ChatTopBarState(
            userName = userName,
            avatar = avatar,
            channelKind = channelKind,
            botName = botName,
            channelPhoto = channelPhoto,
            isBotActive = isBotActive
        )

    val listState: ChatListState
        get() = ChatListState(
            messages = messages,
            isLoading = isLoading,
            error = error,
            isLoadingMore = pagination.isLoading,
            hasMore = pagination.hasMore
        )

    val bottomBarState: ChatBottomBarState
        get() = ChatBottomBarState(
            pendingAttachments = pendingAttachments,
            attachmentState = attachmentState
        )
}

sealed interface AttachmentState {
    data object Idle : AttachmentState
    data object Loading : AttachmentState
    data class Error(val message: String) : AttachmentState
}

