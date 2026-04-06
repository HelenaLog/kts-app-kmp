package com.github.helenalog.ktsappkmp.feature.filter.data.mapper

import com.github.helenalog.ktsappkmp.feature.filter.data.remote.dto.UserListDto
import com.github.helenalog.ktsappkmp.feature.filter.domain.model.UserList

fun UserListDto.toDomain() = UserList(
    id = id,
    name = name,
    tag = tag
)
