package com.github.helenalog.ktsappkmp.core.data.storage

import com.github.helenalog.ktsappkmp.data.storage.ProfileStorage
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.ProjectDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SessionManager(
    private val sessionStorage: SessionStorage,
    private val profileStorage: ProfileStorage,
    private val conversationDao: ConversationDao,
    private val projectDao: ProjectDao,
    private val cabinetDao: CabinetDao
) {
    suspend fun logout() = withContext(Dispatchers.IO) {
        sessionStorage.clearSession()
        profileStorage.clear()
        conversationDao.deleteAll()
        projectDao.deleteAll()
        cabinetDao.deleteAll()
    }
}