package com.github.helenalog.ktsappkmp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.navigation.NavigationGraph
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        NavigationGraph()
    }
}

@Preview
@Composable
private fun AppPreview() {
    App()
}
