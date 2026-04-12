package com.github.helenalog.ktsappkmp.core.data.workspace.repository

import com.github.helenalog.ktsappkmp.core.data.storage.ActiveWorkspaceStore
import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.cabinet.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project
import com.github.helenalog.ktsappkmp.core.domain.project.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.Workspace
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class WorkspaceRepositoryImpl(
    private val cabinetRepository: CabinetRepository,
    private val projectRepository: ProjectRepository,
    private val activeStore: ActiveWorkspaceStore,
) : WorkspaceRepository {

    private val cabinets = MutableStateFlow<List<Cabinet>>(emptyList())
    private val projects = MutableStateFlow<List<Project>>(emptyList())

    override fun observeCabinets(): Flow<List<Cabinet>> = cabinets.asStateFlow()

    override fun observeProjects(): Flow<List<Project>> = projects.asStateFlow()

    override fun observeActive(): Flow<Workspace?> =
        combine(cabinets, projects, activeStore.observe()) { cabs, projs, id ->
            resolveActive(cabs, projs, id)
        }

    override suspend fun selectCabinet(cabinetId: String): Result<Unit> {
        activeStore.save(WorkspaceId(cabinetId, ""))
            .getOrElse { return Result.failure(it) }

        val newProjects = projectRepository.getProjects()
            .getOrElse { return Result.failure(it) }
        projects.value = newProjects

        val firstProject = newProjects.firstOrNull()
            ?: return Result.failure(IllegalStateException("No projects in cabinet"))
        return activeStore.save(WorkspaceId(cabinetId, firstProject.id))
    }

    override suspend fun selectProject(projectId: String): Result<Unit> {
        val current = activeStore.observe().first()
            ?: return Result.failure(IllegalStateException("No active cabinet"))
        return activeStore.save(WorkspaceId(current.cabinetId, projectId))
    }

    override suspend fun refresh(): Result<Unit> {
        val loadedCabinets = cabinetRepository.getCabinets()
            .getOrElse { return Result.failure(it) }
        val loadedProjects = projectRepository.getProjects()
            .getOrElse { return Result.failure(it) }

        cabinets.value = loadedCabinets
        projects.value = loadedProjects
        return Result.success(Unit)
    }

    private fun resolveActive(
        cabinets: List<Cabinet>,
        projects: List<Project>,
        id: WorkspaceId?,
    ): Workspace? {
        if (id == null) return null
        val cabinet = cabinets.firstOrNull { it.id == id.cabinetId } ?: return null
        val project = projects.firstOrNull { it.id == id.projectId } ?: return null
        return Workspace(cabinet, project)
    }
}
