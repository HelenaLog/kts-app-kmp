package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository
import io.github.vinceglb.filekit.core.PlatformFile

class UploadAttachmentUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(
        file: PlatformFile
    ): Result<ChatAttachment> = repository.uploadAttachment(file)
}
