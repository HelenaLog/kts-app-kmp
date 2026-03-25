package com.github.helenalog.ktsappkmp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.ProjectDao
import com.github.helenalog.ktsappkmp.feature.profile.data.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.entity.ConversationEntity
import com.github.helenalog.ktsappkmp.feature.profile.data.local.entity.ProjectEntity

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