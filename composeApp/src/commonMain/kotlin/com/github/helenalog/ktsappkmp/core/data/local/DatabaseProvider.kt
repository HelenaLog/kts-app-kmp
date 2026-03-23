package com.github.helenalog.ktsappkmp.core.data.local

import androidx.sqlite.driver.bundled.BundledSQLiteDriver

object DatabaseProvider {
    val instance: AppDatabase by lazy {
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}