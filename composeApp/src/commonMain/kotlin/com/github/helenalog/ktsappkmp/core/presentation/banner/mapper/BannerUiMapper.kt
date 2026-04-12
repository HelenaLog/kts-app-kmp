package com.github.helenalog.ktsappkmp.core.presentation.banner.mapper

import com.github.helenalog.ktsappkmp.core.domain.banner.model.AppBanner
import com.github.helenalog.ktsappkmp.core.presentation.banner.model.AppBannerUi

fun AppBanner.toUi() = AppBannerUi(
    id = id,
    type = type,
    message = message,
    actionLabel = actionLabel,
    action = action
)
