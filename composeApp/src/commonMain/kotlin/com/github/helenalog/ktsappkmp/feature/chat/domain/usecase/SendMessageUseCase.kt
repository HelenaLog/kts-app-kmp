package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(conversationId: Long, text: String): Result<Unit> {
        if (text.isBlank())
            return Result.failure(
                IllegalArgumentException("Сообщение не может быть пустым")
            )
        return repository.sendMessage(conversationId, text)
    }
}