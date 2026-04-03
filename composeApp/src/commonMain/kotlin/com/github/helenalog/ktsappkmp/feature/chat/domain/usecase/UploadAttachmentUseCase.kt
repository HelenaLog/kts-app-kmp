package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository

class UploadAttachmentUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(
        fileName: String,
        bytes: ByteArray,
        mimeType: String,
    ): Result<ChatAttachment> = repository.uploadAttachment(fileName, bytes, mimeType)
}
