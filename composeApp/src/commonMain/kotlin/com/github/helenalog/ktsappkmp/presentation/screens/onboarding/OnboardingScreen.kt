package com.github.helenalog.ktsappkmp.presentation.screens.onboarding

import com.github.helenalog.ktsappkmp.presentation.ui.components.AppButton
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.compose_multiplatform
import ktsappkmp.composeapp.generated.resources.onboarding_button_get_started
import ktsappkmp.composeapp.generated.resources.onboarding_title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(all = Dimensions.screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://cdni.iconscout.com/illustration/premium/thumb/login-6262949-5253376.png",
                contentDescription = null,
                contentScale = ContentScale.Fit,
                placeholder = painterResource(Res.drawable.compose_multiplatform),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.5f)
            )
            Spacer(modifier = Modifier.height(Dimensions.spacingLarge))
            Text(
                stringResource(Res.string.onboarding_title).uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimensions.spacingXLarge))
            AppButton(
                text = stringResource(Res.string.onboarding_button_get_started),
                onClick = onNavigateToLogin
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPrev() {
    OnboardingScreen(
        onNavigateToLogin = {}
    )
}