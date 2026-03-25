package com.github.helenalog.ktsappkmp.feature.profile.data.remote.response

import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.ManagerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoResponse(
    @SerialName("manager")
    val manager: ManagerDto
)