package com.github.helenalog.ktsappkmp.feature.chat.presentation.model

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatMessageUi

@Immutable
sealed class ChatListItemUi {
    abstract val id: String

    @Immutable
    data class Message(val data: ChatMessageUi) : ChatListItemUi() {
        override val id: String = data.id
    }

    @Immutable
    data class DateHeader(val text: String, val dateKey: String) : ChatListItemUi() {
        override val id: String = "date_$dateKey"
    }
}
