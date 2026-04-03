package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(
        conversationId: Long,
        text: String?,
        attachments: List<ChatAttachment>
    ): Result<Unit> {
        val trimmedText = text?.trim()?.ifBlank { null }
        if (trimmedText == null && attachments.isEmpty())
            return Result.failure(
                IllegalArgumentException(EMPTY_MESSAGE_ERROR)
            )
        return repository.sendMessage(conversationId, trimmedText, attachments = attachments)
    }

    private companion object {
        const val EMPTY_MESSAGE_ERROR = "Сообщение не может быть пустым"
    }
}
