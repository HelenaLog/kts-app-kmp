package com.github.helenalog.ktsappkmp.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object Login : Screen

    @Serializable
    data object Main : Screen

    @Serializable
    data object Profile : Screen

    @Serializable
    data object Tabs : Screen

    @Serializable
    data class Chat(
        val conversationId: Long,
        val userId: String,
    ) : Screen
}