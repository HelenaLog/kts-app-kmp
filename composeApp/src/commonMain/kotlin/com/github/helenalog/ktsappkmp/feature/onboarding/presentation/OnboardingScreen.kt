package com.github.helenalog.ktsappkmp.feature.onboarding.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import ktsappkmp.composeapp.generated.resources.Res
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AppButton
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AppButtonStyle
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.PageContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.PageIndicator
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.onboarding.presentation.OnboardingUiEvent
import com.github.helenalog.ktsappkmp.feature.onboarding.presentation.OnboardingViewModel
import ktsappkmp.composeapp.generated.resources.onboarding_button_back
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.onboarding_background
import ktsappkmp.composeapp.generated.resources.onboarding_button_back
import ktsappkmp.composeapp.generated.resources.onboarding_button_get_started
import ktsappkmp.composeapp.generated.resources.onboarding_button_next
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit = {},
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is OnboardingUiEvent.OnboardingCompleted -> onNavigateToLogin()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.onboarding_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = Dimensions.onboardingHorizontalPadding,
                    vertical = Dimensions.onboardingVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(Dimensions.spacingLarge))
            AnimatedContent(
                targetState = state.currentPage,
                label = "pageContent"
            ) { index ->
                state.currentPageContent?.let { page ->
                    PageContent(page = page)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimensions.onboardingBottomSpacing)
            ) {
                PageIndicator(
                    pageSize = state.totalPages,
                    selectedPage = state.currentPage,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    onPageClick = { page -> viewModel.onPageSelected(page) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        if (state.isFirstPage) Arrangement.Center
                        else Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!state.isFirstPage) {
                        AppButton(
                            text = stringResource(Res.string.onboarding_button_back),
                            onClick = { viewModel.previousPage() },
                            style = AppButtonStyle.Outline,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = Dimensions.onboardingButtonSpacing)
                        )
                    }
                    AppButton(
                        text = if (state.isLastPage) stringResource(Res.string.onboarding_button_get_started)
                        else stringResource(Res.string.onboarding_button_next),
                        onClick = { viewModel.nextPage() },
                        style = AppButtonStyle.Primary,
                        modifier = Modifier
                            .weight(1f)
                            .then(
                                if (!state.isFirstPage)
                                    Modifier.padding(start = Dimensions.onboardingButtonSpacing)
                                else Modifier
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPrev() {
    AppTheme {
        OnboardingScreen(
            onNavigateToLogin = {}
        )
    }
}