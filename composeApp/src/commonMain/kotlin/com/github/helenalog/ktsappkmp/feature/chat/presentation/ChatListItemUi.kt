package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable

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