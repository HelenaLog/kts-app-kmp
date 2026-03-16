package com.github.helenalog.ktsappkmp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.helenalog.ktsappkmp.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.data.local.entity.ConversationEntity

@Database(
    entities = [ConversationEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
}