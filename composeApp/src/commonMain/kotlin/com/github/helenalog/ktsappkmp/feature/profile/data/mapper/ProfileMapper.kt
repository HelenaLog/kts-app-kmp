package com.github.helenalog.ktsappkmp.feature.profile.data.mapper

import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.ManagerDto
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile

fun ManagerDto.toDomain() = Profile(
    id = id,
    email = email,
    name = name ?: "",
    avatarUrl = null
)

