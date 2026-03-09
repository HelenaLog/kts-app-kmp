package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.ConversationDto

interface ConversationRepository {
    suspend fun getList(): List<ConversationDto>
}