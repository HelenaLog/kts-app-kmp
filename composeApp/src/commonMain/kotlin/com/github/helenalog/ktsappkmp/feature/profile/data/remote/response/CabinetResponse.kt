package com.github.helenalog.ktsappkmp.feature.profile.data.remote.response

import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.CabinetDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CabinetResponse(
    @SerialName("cabinets")
    val cabinets: List<CabinetDto>
)
