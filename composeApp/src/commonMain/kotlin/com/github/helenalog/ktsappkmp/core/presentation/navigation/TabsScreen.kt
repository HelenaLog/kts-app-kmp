package com.github.helenalog.ktsappkmp.core.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.UrlLauncher
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationScreen
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi
import com.github.helenalog.ktsappkmp.feature.profile.presentation.ProfileScreen
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_ic_user_info
import org.jetbrains.compose.resources.painterResource
import ktsappkmp.composeapp.generated.resources.tabs_chats
import ktsappkmp.composeapp.generated.resources.tabs_profile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun TabsScreen(
    onLogout: () -> Unit,
    onNavigateToChat: (ConversationUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val urlLauncher = koinInject<UrlLauncher>()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier,
        bottomBar = {
            Column {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = Dimensions.socialButtonBorderWidth
                )
                NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<Screen.Main>() == true,
                        onClick = {
                            navController.navigate(Screen.Main) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Outlined.ChatBubbleOutline,
                                contentDescription = null
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<Screen.Profile>() == true,
                        onClick = {
                            navController.navigate(Screen.Profile) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(Res.drawable.chat_ic_user_info),
                                contentDescription = null
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Main> {
                ConversationScreen(
                    onConversationClick = onNavigateToChat,
                    onOpenUrl = { urlLauncher.openUrl(it) }
                )
            }
            composable<Screen.Profile> { ProfileScreen(onLogout = onLogout) }
        }
    }
}
