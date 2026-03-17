package com.github.helenalog.ktsappkmp.data.remote.dto.response

import com.github.helenalog.ktsappkmp.data.remote.dto.ManagerDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoResponse(
    val manager: ManagerDto
)
