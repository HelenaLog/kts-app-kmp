package com.github.helenalog.ktsappkmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.github.helenalog.ktsappkmp.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.data.storage.SettingsStorage
import com.github.helenalog.ktsappkmp.presentation.screens.login.LoginScreen
import com.github.helenalog.ktsappkmp.presentation.screens.main.MainScreen
import com.github.helenalog.ktsappkmp.presentation.screens.onboarding.OnboardingScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val settings: SettingsStorage = remember { DataStoreSettingsStorage() }
    val isOnboardingDone by settings.isOnboardingDone()
        .collectAsStateWithLifecycle(initialValue = null)

    val startDestination = when (isOnboardingDone) {
        null -> return
        true -> Screen.Login
        false -> Screen.Onboarding
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Onboarding> {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Screen.Login> {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main) {
                        popUpTo(Screen.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Screen.Main> {
            MainScreen()
        }
    }
}