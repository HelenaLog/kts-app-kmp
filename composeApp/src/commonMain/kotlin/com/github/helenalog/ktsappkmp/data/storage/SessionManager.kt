package com.github.helenalog.ktsappkmp.data.storage

import com.github.helenalog.ktsappkmp.data.local.DatabaseProvider
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching

object SessionManager {
    suspend fun logout() {
        suspendRunCatching { SessionProvider.instance.clearSession() }
        suspendRunCatching { DatabaseProvider.instance.conversationDao().deleteAll() }
        suspendRunCatching { DatabaseProvider.instance.profileDao().deleteAll() }
        suspendRunCatching { DatabaseProvider.instance.projectDao().deleteAll() }
        suspendRunCatching { DatabaseProvider.instance.cabinetDao().deleteAll() }
    }
}