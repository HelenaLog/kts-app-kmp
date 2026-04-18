package com.github.helenalog.ktsappkmp.core.data.banner.mapper

import com.github.helenalog.ktsappkmp.core.data.remote.dto.BannerDto
import com.github.helenalog.ktsappkmp.core.domain.banner.model.AppBanner
import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerAction
import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerType

fun BannerDto.toDomain(): AppBanner = AppBanner(
    id = id,
    type = type.toBannerType(),
    message = message,
    actionLabel = actionText,
    action = if (!actionUrl.isNullOrBlank()) {
        BannerAction.OpenUrl(actionUrl)
    } else {
        BannerAction.None
    }
)

private fun String?.toBannerType(): BannerType = when (this?.lowercase()) {
    "error" -> BannerType.ERROR
    else -> BannerType.INFO
}
