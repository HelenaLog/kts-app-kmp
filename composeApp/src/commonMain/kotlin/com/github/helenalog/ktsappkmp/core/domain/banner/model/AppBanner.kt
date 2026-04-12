package com.github.helenalog.ktsappkmp.core.domain.banner.model

data class AppBanner(
    val id: String,
    val type: BannerType,
    val message: String,
    val actionLabel: String?,
    val action: BannerAction
)

sealed interface BannerAction {
    data object None : BannerAction
    data class OpenUrl(val url: String) : BannerAction
    data class OpenChannel(val channelId: String) : BannerAction
}

enum class BannerType {
    INFO, ERROR
}
