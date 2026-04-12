package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository

class SelectProjectUseCase(
    private val repository: WorkspaceRepository
) {
    suspend operator fun invoke(projectId: String): Result<Unit> =
        repository.selectProject(projectId)
}
