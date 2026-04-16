package com.github.helenalog.ktsappkmp.core.data.storage

import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import kotlinx.coroutines.flow.Flow

interface ActiveWorkspaceStore {
    fun observe(): Flow<WorkspaceId?>
    suspend fun save(id: WorkspaceId): Result<Unit>
    suspend fun clear(): Result<Unit>
}
