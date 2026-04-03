package com.github.helenalog.ktsappkmp.feature.profile.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cabinets")
data class CabinetEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "domain")
    val domain: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "created_by")
    val createdBy: String?
)
