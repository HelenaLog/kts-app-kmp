package com.github.helenalog.ktsappkmp.core.data.mapper

import com.github.helenalog.ktsappkmp.data.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.data.local.entity.ProjectEntity
import com.github.helenalog.ktsappkmp.domain.model.Cabinet
import com.github.helenalog.ktsappkmp.domain.model.Project

fun CabinetEntity.toDomain() = Cabinet(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)

fun ProjectEntity.toDomain() = Project(
    id = id,
    name = name
)

fun Cabinet.toEntity() = CabinetEntity(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)

fun Project.toEntity() = ProjectEntity(
    id = id,
    name = name
)