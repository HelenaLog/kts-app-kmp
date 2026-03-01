package com.github.helenalog.ktsappkmp.screens.main.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class MainUiState(
    val friends: List<Friend>
) {
    companion object {
        val Initial = MainUiState(
            friends = emptyList()
        )
    }
}