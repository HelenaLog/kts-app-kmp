package com.github.helenalog.ktsappkmp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.helenalog.ktsappkmp.data.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.domain.model.Cabinet

@Dao
interface CabinetDao {
    @Upsert
    suspend fun upsertAll(cabinets: List<CabinetEntity>)

    @Query("SELECT * FROM cabinets")
    suspend fun getAll(): List<CabinetEntity>

    @Query("DELETE FROM cabinets")
    suspend fun deleteAll()
}