package com.github.helenalog.ktsappkmp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.github.helenalog.ktsappkmp.screens.login.presentation.LoginScreen
import com.github.helenalog.ktsappkmp.screens.onboarding.OnboardingScreen

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
                    navController.navigate(Screen.Login)
                }
            )
        }
        composable<Screen.Login> {
            LoginScreen()
        }
    }
}