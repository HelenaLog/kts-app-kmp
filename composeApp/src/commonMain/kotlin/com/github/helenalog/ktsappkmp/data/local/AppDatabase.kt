package com.github.helenalog.ktsappkmp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.helenalog.ktsappkmp.data.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.data.local.dao.ProjectDao
import com.github.helenalog.ktsappkmp.data.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.data.local.entity.ConversationEntity
import com.github.helenalog.ktsappkmp.data.local.entity.ProjectEntity

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