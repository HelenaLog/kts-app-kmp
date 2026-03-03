package com.github.helenalog.ktsappkmp.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.presentation.ui.components.AppButton
import com.github.helenalog.ktsappkmp.presentation.ui.components.AppTextField
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.login_email_hint
import ktsappkmp.composeapp.generated.resources.login_button_submit
import ktsappkmp.composeapp.generated.resources.login_title
import ktsappkmp.composeapp.generated.resources.login_password_hint
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel { LoginViewModel() },
    onNavigateToMain: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> onNavigateToMain()
            }
        }
    }

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(all = Dimensions.screenPadding)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(Res.string.login_title),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(Dimensions.spacingXLarge))
            AppTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = stringResource(Res.string.login_email_hint),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(Dimensions.spacingMedium))
            AppTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = stringResource(Res.string.login_password_hint),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(Dimensions.spacingXLarge))
            AppButton(
                text = stringResource(Res.string.login_button_submit),
                onClick = { viewModel.onLoginClicked() },
                enabled = state.isLoginButtonActive
            )
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