package com.github.helenalog.ktsappkmp.feature.profile.domain.usecase

import com.github.helenalog.ktsappkmp.core.data.storage.SessionManager

class LogoutUseCase(
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke() = sessionManager.logout()
}