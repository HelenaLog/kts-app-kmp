package com.github.helenalog.ktsappkmp.feature.filter.data.mapper

import com.github.helenalog.ktsappkmp.feature.filter.data.remote.dto.ShortUserDto
import com.github.helenalog.ktsappkmp.feature.filter.domain.model.UserList

fun ShortUserDto.toDomain() = UserList(
    id = id,
    name = name,
    tag = tag
)
