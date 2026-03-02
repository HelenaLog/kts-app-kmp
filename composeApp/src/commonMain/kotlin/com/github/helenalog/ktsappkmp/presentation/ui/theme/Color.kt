package com.github.helenalog.ktsappkmp.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light
val Primary = Color(0xFF4F46E5)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFE0DEFF)
val Secondary = Color(0xFF7C3AED)
val Background = Color(0xFFFFFFFF)
val Surface = Color(0xFFFFFFFF)
val OnBackground = Color(0xFF09090B)
val OnSurface = Color(0xFF09090B)

// Dark
val PrimaryDark = Color(0xFF818CF8)
val OnPrimaryDark = Color(0xFF1E1B4B)
val PrimaryContainerDark = Color(0xFF3730A3)
val SecondaryDark = Color(0xFFA78BFA)
val BackgroundDark = Color(0xFF1A1A2E)
val SurfaceDark = Color(0xFF1A1A2E)
val OnBackgroundDark = Color(0xFFFAFAFA)
val OnSurfaceDark = Color(0xFFFAFAFA)

// Online/Offline status
val OnlineColor = Color(0xFF4CAF50)
val OfflineColor = Color(0xFFBDBDBD)
val OfflineTextColor = Color(0xFF9E9E9E)

val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    onBackground = OnBackground,
    onSurface = OnSurface,
    onSurfaceVariant = OfflineTextColor
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OfflineTextColor
)