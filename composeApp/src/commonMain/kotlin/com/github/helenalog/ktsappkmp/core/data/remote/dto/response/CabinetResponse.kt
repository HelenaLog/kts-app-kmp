package com.github.helenalog.ktsappkmp.core.data.remote.dto.response

import com.github.helenalog.ktsappkmp.core.data.remote.dto.CabinetDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CabinetResponse(
    @SerialName("cabinets")
    val cabinets: List<CabinetDto>
)