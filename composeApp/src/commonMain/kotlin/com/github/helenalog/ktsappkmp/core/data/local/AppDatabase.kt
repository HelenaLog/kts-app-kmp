package com.github.helenalog.ktsappkmp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.helenalog.ktsappkmp.core.data.cabinet.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.core.data.project.local.dao.ProjectDao
import com.github.helenalog.ktsappkmp.core.data.cabinet.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.entity.ConversationEntity
import com.github.helenalog.ktsappkmp.core.data.project.local.entity.ProjectEntity

@Database(
    entities = [
        ConversationEntity::class,
        CabinetEntity::class,
        ProjectEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun cabinetDao(): CabinetDao
    abstract fun projectDao(): ProjectDao
}
