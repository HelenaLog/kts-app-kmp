package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.domain.model.Friend

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