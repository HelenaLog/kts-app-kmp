package com.github.helenalog.ktsappkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.navigation.NavigationGraph

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationGraph()
    }
}