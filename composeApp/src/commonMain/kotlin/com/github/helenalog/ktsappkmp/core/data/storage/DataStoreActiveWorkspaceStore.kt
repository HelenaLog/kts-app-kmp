package com.github.helenalog.ktsappkmp.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreActiveWorkspaceStore(
    private val dataStore: DataStore<Preferences>
) : ActiveWorkspaceStore {

    override fun observe(): Flow<WorkspaceId?> = dataStore.data.map { prefs ->
        val cabinetId = prefs[CABINET_ID]
        val projectId = prefs[PROJECT_ID]
        if (cabinetId != null && projectId != null) {
            WorkspaceId(cabinetId, projectId)
        } else {
            null
        }
    }

    override suspend fun save(id: WorkspaceId): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs ->
            prefs[CABINET_ID] = id.cabinetId
            prefs[PROJECT_ID] = id.projectId
        }
    }

    override suspend fun clear(): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs ->
            prefs.remove(CABINET_ID)
            prefs.remove(PROJECT_ID)
        }
    }

    private companion object {
        val CABINET_ID = stringPreferencesKey("active_workspace_cabinet_id")
        val PROJECT_ID = stringPreferencesKey("active_workspace_project_id")
    }
}
