package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.project.model.Project
import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow

class ObserveProjectsUseCase(
    private val repository: WorkspaceRepository
) {
    operator fun invoke(): Flow<List<Project>> = repository.observeProjects()
}
