package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository

class RefreshWorkspacesUseCase(
    private val repository: WorkspaceRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.refresh()
}
