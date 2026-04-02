package com.agentic.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AdaryusSage,
    onPrimary = AdaryusPaper,
    primaryContainer = AdaryusLinen,
    onPrimaryContainer = AdaryusInk,
    secondary = AdaryusOcean,
    onSecondary = AdaryusPaper,
    secondaryContainer = AdaryusIvory,
    onSecondaryContainer = AdaryusInk,
    tertiary = AdaryusChampagne,
    onTertiary = AdaryusInk,
    background = AdaryusIvory,
    onBackground = AdaryusInk,
    surface = AdaryusPaper,
    onSurface = AdaryusInk,
    surfaceVariant = AdaryusLinen,
    onSurfaceVariant = AdaryusSlate,
    outline = AdaryusSandLine,
    outlineVariant = AdaryusShadow,
    surfaceTint = AdaryusSage
)

@Composable
fun AgenticAndroidTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
