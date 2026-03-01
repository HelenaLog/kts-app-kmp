package com.github.helenalog.ktsappkmp.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Onboarding: Screen
    @Serializable
    data object Login: Screen
    @Serializable
    data object Main: Screen
}