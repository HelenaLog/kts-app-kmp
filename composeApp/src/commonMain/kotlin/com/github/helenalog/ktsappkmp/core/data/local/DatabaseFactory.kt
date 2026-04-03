package com.github.helenalog.ktsappkmp.core.data.local

import androidx.room.RoomDatabase

expect fun getDatabaseBuilder(context: Any): RoomDatabase.Builder<AppDatabase>