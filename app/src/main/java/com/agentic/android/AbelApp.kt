package com.agentic.android

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.agentic.android.screens.CategoriesScreen
import com.agentic.android.screens.HomeScreen
import com.agentic.android.screens.RequestScreen
import com.agentic.android.screens.ShowcaseScreen
import com.agentic.android.screens.TechnologyGuideScreen

internal enum class AppScreen(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Outlined.Home),
    Categories("Categories", Icons.Outlined.Dashboard),
    AppTypes("App Types", Icons.Outlined.Code),
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
            wideLayout -> 430.dp
            compactHeight -> 330.dp
            compactWidth -> 360.dp
            else -> 390.dp
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
    var selectedRequestTypeName by rememberSaveable { mutableStateOf(BuildRequestType.ProductivityBusiness.name) }

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
                .padding(innerPadding)
                .padding(horizontal = layout.contentPadding, vertical = 12.dp)
        ) {
            AdaryusBackdrop(modifier = Modifier.matchParentSize())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 940.dp)
                    .align(Alignment.TopCenter)
            ) {
                when (currentScreen) {
                    AppScreen.Home -> HomeScreen(
                        layout = layout,
                        onExploreCategories = { currentScreenName = AppScreen.Categories.name },
                        onOpenGuide = { currentScreenName = AppScreen.AppTypes.name },
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

                    AppScreen.AppTypes -> TechnologyGuideScreen(
                        layout = layout,
                        onOpenRequest = {
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
    Surface(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f), shadowElevation = 6.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 940.dp)
                .statusBarsPadding()
                .padding(horizontal = layout.contentPadding, vertical = if (layout.compactWidth) 10.dp else 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.adaryus_logo_mark),
                    contentDescription = "Adaryus Builds",
                    modifier = Modifier.size(if (layout.compactWidth) 38.dp else 42.dp),
                    contentScale = ContentScale.Fit
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "ADARYUS",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Editorial app studio for modern businesses",
                        style = if (layout.compactWidth) MaterialTheme.typography.bodySmall else MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f),
                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.14f)
                                )
                            ),
                            RoundedCornerShape(999.dp)
                        )
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.24f), RoundedCornerShape(999.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = currentScreen.label.uppercase(),
                        style = if (layout.compactWidth) MaterialTheme.typography.bodySmall else MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
            )
        }
    }
}

@Composable
private fun AdaryusBottomNavigation(currentScreen: AppScreen, onScreenSelected: (AppScreen) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.97f), shadowElevation = 12.dp) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .navigationBarsPadding()
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f), RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
        ) {
            AppScreen.entries.forEach { screen ->
                NavigationBarItem(
                    selected = currentScreen == screen,
                    onClick = { onScreenSelected(screen) },
                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                    label = { Text(screen.label) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
private fun AdaryusBackdrop(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.background
                )
            )
        )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 18.dp, end = 16.dp)
                .size(320.dp)
                .background(
                    Brush.radialGradient(
                        listOf(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f), Color.Transparent)
                    ),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(260.dp)
                .background(
                    Brush.radialGradient(
                        listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), Color.Transparent)
                    ),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 12.dp)
                .size(220.dp)
                .background(
                    Brush.radialGradient(
                        listOf(MaterialTheme.colorScheme.secondary.copy(alpha = 0.10f), Color.Transparent)
                    ),
                    CircleShape
                )
        )
        StudioBackdropOverlay(modifier = Modifier.matchParentSize())
    }
}

@Composable
private fun StudioBackdropOverlay(modifier: Modifier = Modifier) {
    val step = with(LocalDensity.current) { 60.dp.toPx() }
    val primaryLine = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
    val secondaryLine = MaterialTheme.colorScheme.secondary.copy(alpha = 0.06f)
    val tertiaryLine = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.08f)
    Canvas(modifier = modifier) {
        var x = 0f
        while (x <= size.width) {
            drawLine(
                color = primaryLine,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
            x += step
        }

        var y = 0f
        while (y <= size.height) {
            drawLine(
                color = Color.White.copy(alpha = 0.03f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
            y += step
        }

        drawLine(
            color = secondaryLine,
            start = Offset(size.width * 0.72f, 0f),
            end = Offset(size.width * 0.18f, size.height),
            strokeWidth = 6f
        )
        drawLine(
            color = tertiaryLine,
            start = Offset(size.width, size.height * 0.24f),
            end = Offset(size.width * 0.42f, size.height),
            strokeWidth = 3f
        )
    }
}
