package com.github.helenalog.ktsappkmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.github.helenalog.ktsappkmp.presentation.screens.login.LoginScreen
import com.github.helenalog.ktsappkmp.presentation.screens.onboarding.OnboardingScreen

@Composable
fun NavigationGraph(
    viewModel: NavigationViewModel = viewModel { NavigationViewModel() },
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToLogin -> {
                    navController.navigate(Screen.Login) { popUpTo(0) }
                }
            }
        }
    }

    when (val state = uiState) {
        is NavigationState.Loading -> {
            return
        }

        is NavigationState.Content -> {
            NavHost(
                navController = navController,
                startDestination = state.startDestination
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
                            navController.navigate(Screen.Tabs) {
                                popUpTo(Screen.Login) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable<Screen.Tabs> {
                    TabsScreen(
                        onLogout = {
                            navController.navigate(Screen.Login) {
                                popUpTo(0)
                            }
                        }
                    )
                }

            }
        }
    }
}