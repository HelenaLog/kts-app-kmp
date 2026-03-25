package com.github.helenalog.ktsappkmp.data.remote.dto.response

import com.github.helenalog.ktsappkmp.data.remote.dto.ManagerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoResponse(
    @SerialName("manager")
    val manager: ManagerDto
)
