package com.github.helenalog.ktsappkmp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.github.helenalog.ktsappkmp.feature.chat.presentation.ChatScreen
import com.github.helenalog.ktsappkmp.feature.login.presentation.LoginScreen
import com.github.helenalog.ktsappkmp.feature.onboarding.presentation.OnboardingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationGraph(
    viewModel: NavigationViewModel = koinViewModel(),
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
                composable<Screen.Chat> { backStack ->
                    val args = backStack.toRoute<Screen.Chat>()
                    ChatScreen(
                        conversationId = args.conversationId,
                        userId = args.userId,
                        onNavigateBack = { navController.popBackStack() },
                        onUserInfo = {},
                    )
                }
            }
        }
    }
}