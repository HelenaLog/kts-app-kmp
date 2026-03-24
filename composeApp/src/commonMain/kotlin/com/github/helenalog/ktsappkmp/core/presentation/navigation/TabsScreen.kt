package com.github.helenalog.ktsappkmp.core.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationScreen
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationUi
import com.github.helenalog.ktsappkmp.feature.profile.presentation.ProfileScreen
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.tabs_chats
import ktsappkmp.composeapp.generated.resources.tabs_profile
import org.jetbrains.compose.resources.stringResource

@Composable
fun TabsScreen(
    onLogout: () -> Unit,
    onNavigateToChat: (ConversationUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<Screen.Main>() == true,
                    onClick = {
                        navController.navigate(Screen.Main) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null) },
                    label = { Text(stringResource(Res.string.tabs_chats)) }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<Screen.Profile>() == true,
                    onClick = {
                        navController.navigate(Screen.Profile) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text(stringResource(Res.string.tabs_profile)) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Main> {
                ConversationScreen(onConversationClick = onNavigateToChat)
            }
            composable<Screen.Profile> { ProfileScreen(onLogout = onLogout) }
        }
    }
}