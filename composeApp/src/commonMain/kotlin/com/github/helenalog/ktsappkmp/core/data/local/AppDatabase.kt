package com.github.helenalog.ktsappkmp.core.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.github.helenalog.ktsappkmp.core.data.cabinet.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.core.data.project.local.dao.ProjectDao
import com.github.helenalog.ktsappkmp.core.data.cabinet.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.entity.ConversationEntity
import com.github.helenalog.ktsappkmp.core.data.project.local.entity.ProjectEntity

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>

@Database(
    entities = [
        ConversationEntity::class,
        CabinetEntity::class,
        ProjectEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun cabinetDao(): CabinetDao
    abstract fun projectDao(): ProjectDao
}
