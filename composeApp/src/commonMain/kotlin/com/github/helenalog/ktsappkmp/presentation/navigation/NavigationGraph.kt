package com.github.helenalog.ktsappkmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.github.helenalog.ktsappkmp.presentation.screens.login.LoginScreen
import com.github.helenalog.ktsappkmp.presentation.screens.main.MainScreen
import com.github.helenalog.ktsappkmp.presentation.screens.onboarding.OnboardingScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding
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