package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CaptchaView(
    siteKey: String,
    onTokenReceive: (String) -> Unit,
    modifier: Modifier = Modifier
)
