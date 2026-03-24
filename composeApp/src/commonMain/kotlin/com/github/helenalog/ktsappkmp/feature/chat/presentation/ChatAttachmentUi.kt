package com.github.helenalog.ktsappkmp.feature.chat.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachmentType

@Immutable
sealed class ChatAttachmentUi {
    abstract val id: String
    abstract val type: ChatAttachmentType

    @Immutable
    data class Image(
        override val id: String,
        val url: String,
        override val type: ChatAttachmentType = ChatAttachmentType.IMAGE
    ) : ChatAttachmentUi()

    @Immutable
    data class File(
        override val id: String,
        val typeLabel: String,
        val name: String,
        val sizeLabel: String,
        val extension: String,
        override val type: ChatAttachmentType = ChatAttachmentType.FILE,
    ) : ChatAttachmentUi()
}