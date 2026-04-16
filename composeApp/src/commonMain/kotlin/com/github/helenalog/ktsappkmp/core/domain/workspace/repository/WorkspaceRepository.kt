package com.github.helenalog.ktsappkmp.core.domain.workspace.repository

import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.Workspace
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    fun observeCabinets(): Flow<List<Cabinet>>
    fun observeProjects(): Flow<List<Project>>
    fun observeActive(): Flow<Workspace?>
    suspend fun selectCabinet(cabinetId: String): Result<Unit>
    suspend fun selectProject(projectId: String): Result<Unit>
    suspend fun refresh(): Result<Unit>
}
