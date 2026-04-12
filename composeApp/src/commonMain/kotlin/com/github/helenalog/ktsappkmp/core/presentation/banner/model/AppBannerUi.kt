package com.github.helenalog.ktsappkmp.core.presentation.banner.model

import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerAction
import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerType

data class AppBannerUi(
    val id: String,
    val type: BannerType,
    val message: String,
    val actionLabel: String?,
    val action: BannerAction
)
