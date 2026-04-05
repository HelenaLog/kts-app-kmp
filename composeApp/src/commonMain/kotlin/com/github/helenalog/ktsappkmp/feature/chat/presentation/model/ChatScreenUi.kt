package com.github.helenalog.ktsappkmp.feature.chat.presentation.model

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.AttachmentState
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

@Immutable
data class ChatTopBarState(
    val userName: String,
    val avatar: UserAvatarUi,
    val channelKind: ChannelKind,
    val botName: String,
    val channelPhoto: String?,
    val isBotActive: Boolean
)

@Immutable
data class ChatListState(
    val messages: List<ChatListItemUi>,
    val isLoading: Boolean,
    val error: String?,
    val isLoadingMore: Boolean,
    val hasMore: Boolean
)

@Immutable
data class ChatBottomBarState(
    val pendingAttachments: List<ChatAttachmentUi>,
    val attachmentState: AttachmentState
)
