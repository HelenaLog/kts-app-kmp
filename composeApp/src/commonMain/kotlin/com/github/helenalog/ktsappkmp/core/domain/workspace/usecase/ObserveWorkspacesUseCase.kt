package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.workspace.model.Workspace
import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow

class ObserveWorkspacesUseCase(
    private val repository: WorkspaceRepository
) {
    operator fun invoke(): Flow<List<Workspace>> = repository.observeAll()
}
