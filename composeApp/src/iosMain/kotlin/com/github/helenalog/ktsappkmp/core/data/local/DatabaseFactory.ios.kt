package com.github.helenalog.ktsappkmp.core.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private const val DB_NAME = "app_database.db"

@OptIn(ExperimentalForeignApi::class)
actual fun getDatabaseBuilder(context: Any): RoomDatabase.Builder<AppDatabase> {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    val dbFilePath = requireNotNull(documentDirectory).path + "/$DB_NAME"
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
}
