package com.agentic.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AdaryusNeonCyan,
    onPrimary = AdaryusDarker,
    primaryContainer = AdaryusSteel,
    onPrimaryContainer = AdaryusWhite,
    secondary = AdaryusNeonPurple,
    onSecondary = AdaryusWhite,
    secondaryContainer = AdaryusDarker,
    onSecondaryContainer = AdaryusWhite,
    background = AdaryusDark,
    onBackground = AdaryusWhite,
    surface = AdaryusSteel,
    onSurface = AdaryusWhite,
    surfaceVariant = AdaryusDarker,
    onSurfaceVariant = AdaryusMist,
    outline = AdaryusGray
)

@Composable
fun AgenticAndroidTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
