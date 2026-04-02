package com.agentic.android

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.agentic.android.screens.CategoriesScreen
import com.agentic.android.screens.HomeScreen
import com.agentic.android.screens.RequestScreen
import com.agentic.android.screens.ShowcaseScreen

internal enum class AppScreen(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Outlined.Home),
    Categories("Categories", Icons.Outlined.Dashboard),
    Showcase("Showcase", Icons.Outlined.Palette),
    Request("Request", Icons.Outlined.Email)
}

internal data class ResponsiveLayout(
    val compactWidth: Boolean,
    val compactHeight: Boolean,
    val singleColumn: Boolean,
    val wideLayout: Boolean,
    val formTwoColumn: Boolean,
    val contentPadding: Dp,
    val heroHeight: Dp,
    val photoHeight: Dp,
    val webViewHeight: Dp,
    val topBarLogoWidth: Dp,
    val heroLogoWidth: Dp
)

@Composable
internal fun rememberResponsiveLayout(): ResponsiveLayout {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val wideLayout = screenWidth >= 840
    val compactWidth = screenWidth < 380
    val singleColumn = screenWidth < 430
    val compactHeight = screenHeight < 700

    return ResponsiveLayout(
        compactWidth = compactWidth,
        compactHeight = compactHeight,
        singleColumn = singleColumn,
        wideLayout = wideLayout,
        formTwoColumn = screenWidth >= 640,
        contentPadding = when {
            wideLayout -> 24.dp
            compactWidth -> 12.dp
            else -> 16.dp
        },
        heroHeight = when {
            wideLayout -> 420.dp
            compactHeight -> 320.dp
            compactWidth -> 360.dp
            else -> 380.dp
        },
        photoHeight = when {
            wideLayout -> 220.dp
            compactWidth -> 164.dp
            else -> 188.dp
        },
        webViewHeight = when {
            wideLayout -> 520.dp
            compactHeight -> 320.dp
            else -> 420.dp
        },
        topBarLogoWidth = 164.dp,
        heroLogoWidth = 132.dp
    )
}

@Composable
internal fun AdaryusBuildsApp() {
    val layout = rememberResponsiveLayout()
    var currentScreenName by rememberSaveable { mutableStateOf(AppScreen.Home.name) }
    var selectedRequestTypeName by rememberSaveable { mutableStateOf(BuildRequestType.BusinessSuite.name) }

    val currentScreen = AppScreen.valueOf(currentScreenName)
    val selectedRequestType = BuildRequestType.valueOf(selectedRequestTypeName)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AdaryusTopBar(layout, currentScreen) },
        bottomBar = {
            AdaryusBottomNavigation(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreenName = it.name }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = layout.contentPadding, vertical = 12.dp)
        ) {
            CyberGridOverlay(modifier = Modifier.matchParentSize())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 920.dp)
                    .align(Alignment.TopCenter)
            ) {
                when (currentScreen) {
                    AppScreen.Home -> HomeScreen(
                        layout = layout,
                        onExploreCategories = { currentScreenName = AppScreen.Categories.name },
                        onOpenShowcase = { currentScreenName = AppScreen.Showcase.name },
                        onRequestBuild = {
                            selectedRequestTypeName = it.name
                            currentScreenName = AppScreen.Request.name
                        }
                    )

                    AppScreen.Categories -> CategoriesScreen(
                        layout = layout,
                        onStartRequest = {
                            selectedRequestTypeName = it.name
                            currentScreenName = AppScreen.Request.name
                        }
                    )

                    AppScreen.Showcase -> ShowcaseScreen(
                        layout = layout,
                        onOpenRequest = {
                            selectedRequestTypeName = it.name
                            currentScreenName = AppScreen.Request.name
                        }
                    )

                    AppScreen.Request -> RequestScreen(
                        layout = layout,
                        selectedRequestType = selectedRequestType,
                        onRequestTypeSelected = { selectedRequestTypeName = it.name }
                    )
                }
            }
        }
    }
}

@Composable
private fun AdaryusTopBar(layout: ResponsiveLayout, currentScreen: AppScreen) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
        shadowElevation = 6.dp,
        tonalElevation = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 920.dp)
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(horizontal = layout.contentPadding, vertical = if (layout.compactWidth) 10.dp else 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(contentAlignment = Alignment.CenterStart) {
                    Image(
                        painter = painterResource(id = R.drawable.adaryus_logo_mark),
                        contentDescription = "Adaryus Builds",
                        modifier = Modifier
                            .size(if (layout.compactWidth) 34.dp else 38.dp),
                        contentScale = ContentScale.Fit
                    )
                    Column(modifier = Modifier.padding(start = 48.dp)) {
                        Text(
                            text = "ADARYUS",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = currentScreen.label,
                            style = if (layout.compactWidth) MaterialTheme.typography.bodySmall else MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AdaryusBottomNavigation(
    currentScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.navigationBarsPadding()
    ) {
        AppScreen.entries.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onScreenSelected(screen) },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}

@Composable
private fun CyberGridOverlay(modifier: Modifier = Modifier) {
    val step = with(LocalDensity.current) { 60.dp.toPx() }
    val verticalLineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    val horizontalLineColor = Color.White.copy(alpha = 0.035f)
    Canvas(modifier = modifier) {
        var x = 0f
        while (x <= size.width) {
            drawLine(
                color = verticalLineColor,
                start = androidx.compose.ui.geometry.Offset(x, 0f),
                end = androidx.compose.ui.geometry.Offset(x, size.height),
                strokeWidth = 1f
            )
            x += step
        }

        var y = 0f
        while (y <= size.height) {
            drawLine(
                color = horizontalLineColor,
                start = androidx.compose.ui.geometry.Offset(0f, y),
                end = androidx.compose.ui.geometry.Offset(size.width, y),
                strokeWidth = 1f
            )
            y += step
        }
    }
}
