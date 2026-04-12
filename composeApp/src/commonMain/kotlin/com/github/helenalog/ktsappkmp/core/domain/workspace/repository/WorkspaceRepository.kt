package com.github.helenalog.ktsappkmp.core.domain.workspace.repository

import com.github.helenalog.ktsappkmp.core.domain.workspace.model.Workspace
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    fun observeActive(): Flow<Workspace?>
    fun observeAll(): Flow<List<Workspace>>
    suspend fun selectActive(id: WorkspaceId): Result<Unit>
    suspend fun refresh(): Result<Unit>
}
