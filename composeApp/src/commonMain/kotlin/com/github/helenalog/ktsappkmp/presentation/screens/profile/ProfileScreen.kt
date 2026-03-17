package com.github.helenalog.ktsappkmp.presentation.screens.profile

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.presentation.ui.components.AppButton
import com.github.helenalog.ktsappkmp.presentation.ui.components.UserAvatar
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.presentation.ui.theme.SocialButtonBorder
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.profile_cabinets
import ktsappkmp.composeapp.generated.resources.profile_logout
import ktsappkmp.composeapp.generated.resources.profile_projects
import ktsappkmp.composeapp.generated.resources.profile_retry
import ktsappkmp.composeapp.generated.resources.profile_settings
import ktsappkmp.composeapp.generated.resources.profile_seсtions_no_data
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = viewModel { ProfileViewModel() },
    modifier: Modifier = Modifier
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
                    ProfileErrorView(
                        message = state.profileError ?: "",
                        onRetry = { viewModel.retryProfile() }
                    )
                }
                else -> {
                    ProfileContent(
                        state = state,
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
    state: ProfileUiState,
    onLogout: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayName = state.name.ifBlank { state.email.orEmpty() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.screenPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        ProfileHeader(
            name = displayName,
            email = state.email,
            avatarUrl = state.avatarUrl,
            onLogoutClick = onLogout,
        )
        Spacer(modifier = Modifier.height(Dimensions.spacingLarge))
        SectionTitle(text = stringResource(Res.string.profile_cabinets))
        ProfileSectionList(
            isLoading = state.isCabinetsLoading,
            error = state.cabinetsError,
            items = state.cabinetNames,
            showArrow = true,
            onSectionClick = {}
        )
        Spacer(modifier = Modifier.height(Dimensions.spacingMedium))
        SectionTitle(text = stringResource(Res.string.profile_projects))
        ProfileSectionList(
            isLoading = state.isProjectsLoading,
            error = state.projectsError,
            items = state.projectNames,
            showArrow = true,
            onSectionClick = {}
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
    avatarUrl: String?,
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
        UserAvatar(name = name, photoUrl = avatarUrl)
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
private fun ProfileErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding((Dimensions.screenPadding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(Dimensions.spacingSmall))
        AppButton(
            text = stringResource(Res.string.profile_retry),
            onClick = onRetry
        )
    }
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier.padding(Dimensions.spacingSmall)
    )
}

@Composable
private fun ProfileSectionList(
    items: List<String>,
    isLoading: Boolean,
    error: String?,
    onSectionClick: () -> Unit,
    modifier: Modifier = Modifier,
    showArrow: Boolean = false
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = !isLoading,
                onClick = onSectionClick
            ),
        shape = RoundedCornerShape(Dimensions.textFieldCornerRadius),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(Dimensions.socialButtonBorderWidth, SocialButtonBorder),
    ) {
        Row(
            modifier = Modifier.padding(Dimensions.textFieldPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when {
                    isLoading -> CircularProgressIndicator(
                        modifier = Modifier.size(Dimensions.loaderSize),
                        strokeWidth = Dimensions.loaderStroke,
                        color = MaterialTheme.colorScheme.primary
                    )
                    error != null -> Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    items.isEmpty() -> Text(
                        text = stringResource(Res.string.profile_seсtions_no_data),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    else -> Column {
                        items.forEach { item ->
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
            if (showArrow && !isLoading) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
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