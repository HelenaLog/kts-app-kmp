package com.github.helenalog.ktsappkmp.core.data.remote.dto.response

import com.github.helenalog.ktsappkmp.core.data.remote.dto.ManagerDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoResponse(
    val manager: ManagerDto
)
