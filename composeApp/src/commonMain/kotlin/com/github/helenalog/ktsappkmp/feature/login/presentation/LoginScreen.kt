package com.github.helenalog.ktsappkmp.feature.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.helenalog.ktsappkmp.BuildKonfig
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AppButton
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AppTextField
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.CaptchaView
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.SocialLoginRow
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.app_name
import ktsappkmp.composeapp.generated.resources.login_email_hint
import ktsappkmp.composeapp.generated.resources.login_button_submit
import ktsappkmp.composeapp.generated.resources.login_forgot_password
import ktsappkmp.composeapp.generated.resources.login_label_email
import ktsappkmp.composeapp.generated.resources.login_label_password
import ktsappkmp.composeapp.generated.resources.login_title
import ktsappkmp.composeapp.generated.resources.login_password_hint
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> onNavigateToMain()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(all = Dimensions.screenPadding)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingLarge))
            Text(
                text = stringResource(Res.string.login_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingLarge))
            Text(
                text = stringResource(Res.string.login_label_email),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingSmall))
            AppTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                placeholder = stringResource(Res.string.login_email_hint),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingMedium))
            Text(
                text = stringResource(Res.string.login_label_password),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingSmall))
            AppTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                placeholder = stringResource(Res.string.login_password_hint),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingLarge))
            Text(
                text = stringResource(Res.string.login_forgot_password),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingLarge))
            CaptchaView(
                siteKey = BuildKonfig.CAPTCHA_SITE_KEY,
                onTokenReceived = viewModel::onCaptchaTokenReceived,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.captchaHeight)
            )
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingLarge))
            AppButton(
                text = stringResource(Res.string.login_button_submit),
                onClick = { viewModel.onLoginClicked() },
                enabled = state.isLoginButtonActive,
                isLoading = state.isLoading
            )
            if (state.error.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimensions.spacingMedium))
                Text(
                    text = state.error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(Dimensions.loginSpacingMedium))
            SocialLoginRow()
        }
    }
}

@Preview
@Composable
private fun LoginScreenPrev() {
    LoginScreen(
        onNavigateToMain = {}
    )
}