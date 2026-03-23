package com.github.helenalog.ktsappkmp.core.data.storage

import com.github.helenalog.ktsappkmp.core.data.local.DatabaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

object SessionManager {
    suspend fun logout() = withContext(Dispatchers.IO) {
        SessionProvider.instance.clearSession()
        DataStoreProfileStorage().clear()
        DatabaseProvider.instance.let { db ->
            db.conversationDao().deleteAll()
            db.projectDao().deleteAll()
            db.cabinetDao().deleteAll()
        }
    }
}