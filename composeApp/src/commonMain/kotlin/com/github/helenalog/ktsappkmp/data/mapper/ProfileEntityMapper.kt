package com.github.helenalog.ktsappkmp.data.mapper

import com.github.helenalog.ktsappkmp.data.local.entity.CabinetEntity
import com.github.helenalog.ktsappkmp.data.local.entity.ProfileEntity
import com.github.helenalog.ktsappkmp.data.local.entity.ProjectEntity
import com.github.helenalog.ktsappkmp.domain.model.Cabinet
import com.github.helenalog.ktsappkmp.domain.model.Profile
import com.github.helenalog.ktsappkmp.domain.model.Project

fun ProfileEntity.toDomain() = Profile(
    id = id,
    email = email ?: "",
    name = name,
    avatarUrl = avatarUrl
)

fun ProjectEntity.toDomain() = Project(
    id = id,
    name = name
)

fun CabinetEntity.toDomain() = Cabinet(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)

fun Profile.toEntity() = ProfileEntity(
    id = id,
    email = email,
    name = name,
    avatarUrl = avatarUrl
)

fun Project.toEntity() = ProjectEntity(
    id = id,
    name = name
)

fun Cabinet.toEntity() = CabinetEntity(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)