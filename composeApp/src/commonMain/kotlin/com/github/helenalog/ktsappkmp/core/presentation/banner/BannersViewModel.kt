package com.github.helenalog.ktsappkmp.core.presentation.banner

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerAction
import com.github.helenalog.ktsappkmp.core.domain.banner.usecase.DismissBannerUseCase
import com.github.helenalog.ktsappkmp.core.domain.banner.usecase.GetBannersUseCase
import com.github.helenalog.ktsappkmp.core.presentation.banner.mapper.toUi
import com.github.helenalog.ktsappkmp.core.presentation.banner.model.AppBannerUi
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class BannersViewModel(
    private val getBanners: GetBannersUseCase,
    private val dismissBanner: DismissBannerUseCase
) : BaseViewModel<BannersUiState, BannersUiEvent>(BannersUiState()) {

    init {
        reload()
    }

    fun onAction(banner: AppBannerUi) {
        when (val action = banner.action) {
            is BannerAction.OpenUrl -> sendEvent(BannersUiEvent.OpenUrl(action.url))
            BannerAction.None -> Unit
        }
    }

    fun onDismiss(banner: AppBannerUi) {
        updateState { copy(banners = banners.filterNot { it.id == banner.id }) }
        viewModelScope.launch {
            dismissBanner(banner.id)
        }
    }

    private fun reload() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            getBanners()
                .onSuccess { banners ->
                    updateState { copy(isLoading = false, banners = banners.map { it.toUi() }) }
                }
                .onFailure { e ->
                    updateState { copy(isLoading = false, error = e.message ?: UNKNOWN_ERROR) }
                }
        }
    }

    private companion object {
        const val UNKNOWN_ERROR = "Неизвестная ошибка"
    }
}
