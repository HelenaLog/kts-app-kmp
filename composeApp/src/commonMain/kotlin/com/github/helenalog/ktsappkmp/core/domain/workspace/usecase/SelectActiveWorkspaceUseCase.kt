package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository

class SelectActiveWorkspaceUseCase(
    private val repository: WorkspaceRepository
) {
    suspend operator fun invoke(id: WorkspaceId): Result<Unit> =
        repository.selectActive(id)
}
