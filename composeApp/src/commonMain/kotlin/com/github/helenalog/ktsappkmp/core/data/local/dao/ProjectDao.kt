package com.github.helenalog.ktsappkmp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.helenalog.ktsappkmp.core.data.local.entity.ProjectEntity

@Dao
interface ProjectDao {
    @Upsert
    suspend fun upsertAll(projects: List<ProjectEntity>)

    @Query("SELECT * FROM projects")
    suspend fun getAll(): List<ProjectEntity>

    @Query("DELETE FROM projects")
    suspend fun deleteAll()
}