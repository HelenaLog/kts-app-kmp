package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class PaginationState(
    val isPaginating: Boolean = false,
    val error: String? = null,
    val hasReachedEnd: Boolean = false,
    val offset: Int = 0,
)