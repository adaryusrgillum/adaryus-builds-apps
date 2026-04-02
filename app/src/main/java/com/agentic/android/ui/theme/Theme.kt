package com.agentic.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = AdaryusEmber,
    onPrimary = AdaryusBlack,
    primaryContainer = AdaryusEmberDeep,
    onPrimaryContainer = AdaryusWhite,
    secondary = AdaryusFlame,
    onSecondary = AdaryusBlack,
    secondaryContainer = AdaryusPanelRaised,
    onSecondaryContainer = AdaryusWhite,
    tertiary = AdaryusMolten,
    onTertiary = AdaryusBlack,
    background = AdaryusBlack,
    onBackground = AdaryusWhite,
    surface = AdaryusPanel,
    onSurface = AdaryusWhite,
    surfaceVariant = AdaryusCarbon,
    onSurfaceVariant = AdaryusSilver,
    outline = AdaryusSmoke,
    outlineVariant = AdaryusSmoke,
    surfaceTint = AdaryusEmber
)

@Composable
fun AgenticAndroidTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
