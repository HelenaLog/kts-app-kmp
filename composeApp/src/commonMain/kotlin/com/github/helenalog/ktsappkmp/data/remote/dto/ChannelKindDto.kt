package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChannelKindDto {
    @SerialName("tg") TG,
    @SerialName("wz") WZ,
    @SerialName("jv") JV,
    @SerialName("unknown") UNKNOWN;
}