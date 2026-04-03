package com.github.helenalog.ktsappkmp.core.presentation.common

import androidx.compose.runtime.Immutable

@Immutable
data class PaginationState(
    val isLoading: Boolean = false,
    val hasMore: Boolean = true,
    val error: String? = null,
    val offset: Int = 0,
    val cursor: String? = null
)
