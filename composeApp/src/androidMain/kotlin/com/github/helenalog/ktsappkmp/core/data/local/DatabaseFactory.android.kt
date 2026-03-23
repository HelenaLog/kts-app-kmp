package com.github.helenalog.ktsappkmp.core.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.helenalog.ktsappkmp.core.data.storage.appContext

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder(
        context = appContext,
        klass = AppDatabase::class.java,
        name = "app_database.db"
    )
}