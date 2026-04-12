package com.github.helenalog.ktsappkmp.core.data.cabinet.mapper

import com.github.helenalog.ktsappkmp.core.data.cabinet.remote.dto.CabinetDto
import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet

fun CabinetDto.toDomain() = Cabinet(
    id = id,
    domain = domain,
    name = name,
    createdBy = createdBy
)
