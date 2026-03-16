package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun AppSwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
)