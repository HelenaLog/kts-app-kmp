package com.github.helenalog.ktsappkmp.core.data.cabinet.mapper

import com.github.helenalog.ktsappkmp.core.data.cabinet.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet

fun CabinetEntity.toDomain() = Cabinet(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)

fun Cabinet.toEntity() = CabinetEntity(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)
