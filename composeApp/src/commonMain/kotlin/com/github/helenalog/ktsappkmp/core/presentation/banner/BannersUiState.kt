package com.github.helenalog.ktsappkmp.core.presentation.banner

import com.github.helenalog.ktsappkmp.core.presentation.banner.model.AppBannerUi

data class BannersUiState(
    val banners: List<AppBannerUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
