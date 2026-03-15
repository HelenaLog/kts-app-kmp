package com.github.helenalog.ktsappkmp

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.presentation.navigation.NavigationGraph
import com.github.helenalog.ktsappkmp.presentation.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        NavigationGraph()
    }
}