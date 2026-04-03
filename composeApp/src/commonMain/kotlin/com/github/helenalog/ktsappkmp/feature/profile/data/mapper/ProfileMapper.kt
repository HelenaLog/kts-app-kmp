package com.github.helenalog.ktsappkmp.feature.profile.data.mapper

import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.CabinetDto
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.ManagerDto
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.ProjectDto
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Cabinet
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Project

fun ManagerDto.toDomain() = Profile(
    id = id,
    email = email,
    name = name ?: "",
    avatarUrl = null
)

fun ProjectDto.toDomain() = Project(
    id = id,
    name = name
)

fun CabinetDto.toDomain() = Cabinet(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)