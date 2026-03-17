package com.github.helenalog.ktsappkmp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.helenalog.ktsappkmp.data.local.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Upsert
    suspend fun upsert(profile: ProfileEntity)

    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun get(): ProfileEntity?

    @Query("DELETE FROM profile")
    suspend fun deleteAll()
}