package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
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
)
