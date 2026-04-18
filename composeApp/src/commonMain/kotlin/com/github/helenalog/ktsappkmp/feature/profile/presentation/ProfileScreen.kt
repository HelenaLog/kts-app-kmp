package com.github.helenalog.ktsappkmp.feature.profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.ErrorContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.UserAvatar
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.profile.presentation.model.ProfileUi
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.profile_logout
import ktsappkmp.composeapp.generated.resources.profile_settings
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is ProfileUiEvent.NavigateToLogin -> onLogout()
            }
        }
    }

    Scaffold(modifier = modifier) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when {
                state.isProfileLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.profileError != null -> {
                    ErrorContent(
                        message = state.profileError ?: "",
                        onRetry = { viewModel.retryProfile() }
                    )
                }

                else -> {
                    ProfileContent(
                        profile = state.profile,
                        onLogout = { viewModel.logout() },
                        onSettingsClick = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    profile: ProfileUi,
    onLogout: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.screenPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        ProfileHeader(
            name = profile.displayName,
            email = profile.email,
            avatar = profile.avatar,
            onLogoutClick = onLogout
        )
        Spacer(modifier = Modifier.height(Dimensions.spacingLarge))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(Dimensions.spacingSmall))
        ProfileSettings(
            icon = Icons.Outlined.Person,
            label = stringResource(Res.string.profile_settings),
            onClick = onSettingsClick
        )
    }
}

@Composable
private fun ProfileHeader(
    name: String,
    email: String?,
    avatar: UserAvatarUi,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium),
    ) {
        UserAvatar(avatar = avatar)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
            )
            if (email != null) {
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        IconButton(onClick = onLogoutClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Logout,
                contentDescription = stringResource(Res.string.profile_logout),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun ProfileSettings(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(Dimensions.spacingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
