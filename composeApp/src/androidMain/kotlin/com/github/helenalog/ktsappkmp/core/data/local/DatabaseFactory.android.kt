package com.github.helenalog.ktsappkmp.core.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabaseBuilder(context: Any): RoomDatabase.Builder<AppDatabase> {
    val ctx = context as Context
    val dbFile = ctx.getDatabasePath(DB_NAME)
    return Room.databaseBuilder(ctx, AppDatabase::class.java, dbFile.absolutePath)
}

private const val DB_NAME = "app_database.db"