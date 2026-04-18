package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.data.storage.ActiveWorkspaceStore
import com.github.helenalog.ktsappkmp.core.domain.cabinet.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.core.domain.project.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import kotlinx.coroutines.flow.first

class WorkspaceBootstrapper(
    private val activeStore: ActiveWorkspaceStore,
    private val cabinetRepository: CabinetRepository,
    private val projectRepository: ProjectRepository
) {
    suspend fun ensureActiveWorkspace(): Result<Unit> {
        if (activeStore.observe().first() != null) return Result.success(Unit)

        val cabinet = cabinetRepository.getCabinets()
            .getOrElse { return Result.failure(it) }
            .firstOrNull() ?: return Result.failure(IllegalStateException(ERROR_NO_CABINETS))

        activeStore.save(WorkspaceId(cabinet.id, "")).getOrElse { return Result.failure(it) }

        val project = projectRepository.getProjects()
            .getOrElse { return Result.failure(it) }
            .firstOrNull() ?: return Result.failure(IllegalStateException(ERROR_NO_PROJECTS))

        return activeStore.save(WorkspaceId(cabinet.id, project.id))
    }

    companion object {
        private const val ERROR_NO_CABINETS = "Нет кабинетов"
        private const val ERROR_NO_PROJECTS = "Нет проектов"
    }
}
