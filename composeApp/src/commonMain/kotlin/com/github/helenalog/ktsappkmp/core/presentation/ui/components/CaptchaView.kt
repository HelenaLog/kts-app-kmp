package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CaptchaView(
    siteKey: String,
    onTokenReceived: (String) -> Unit,
    modifier: Modifier = Modifier
)
