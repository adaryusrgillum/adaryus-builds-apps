package com.agentic.android.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.agentic.android.ActionRow
import com.agentic.android.BuildRequestType
import com.agentic.android.InfoBadge
import com.agentic.android.PosterArtwork
import com.agentic.android.PosterStyle
import com.agentic.android.ResponsiveLayout
import com.agentic.android.SectionCard
import com.agentic.android.SectionTitle
import com.agentic.android.homeQuickActions
import com.agentic.android.showcaseCards
import com.agentic.android.studioHighlights

@Composable
internal fun HomeScreen(
    layout: ResponsiveLayout,
    onExploreCategories: () -> Unit,
    onOpenShowcase: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(600)) + slideInVertically(initialOffsetY = { it / 8 }, animationSpec = tween(600))
        ) {
            HomeHeroSection(layout, onExploreCategories, onRequestBuild)
        }
        PosterGallerySection(layout)
        QuickActionsSection(layout, onExploreCategories, onOpenShowcase, onRequestBuild)
        StudioHighlightsSection()
    }
}

@Composable
private fun HomeHeroSection(
    layout: ResponsiveLayout,
    onExploreCategories: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    val glowTransition = rememberInfiniteTransition(label = "home_hero_glow")
    val glowScale = glowTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.16f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(layout.heroHeight)
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.72f)
                        )
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 28.dp, end = 24.dp)
                    .size(if (layout.wideLayout) 220.dp else 160.dp)
                    .graphicsLayer {
                        scaleX = glowScale.value
                        scaleY = glowScale.value
                        alpha = 0.22f
                    }
                    .background(
                        Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                            )
                        ),
                        CircleShape
                    )
            )

            if (layout.wideLayout) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeroCopy(
                        modifier = Modifier.weight(1.1f),
                        compact = layout.compactWidth,
                        onExploreCategories = onExploreCategories,
                        onRequestBuild = onRequestBuild
                    )
                    HeroPoster(
                        modifier = Modifier.weight(0.9f),
                        compact = layout.compactWidth
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HeroCopy(
                        modifier = Modifier.fillMaxWidth(),
                        compact = layout.compactWidth,
                        onExploreCategories = onExploreCategories,
                        onRequestBuild = onRequestBuild
                    )
                    HeroPoster(
                        modifier = Modifier.fillMaxWidth(),
                        compact = layout.compactWidth
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroCopy(
    modifier: Modifier,
    compact: Boolean,
    onExploreCategories: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "AI • MARKETING • DEVELOPMENT • SCALE",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 4.sp
        )
        Text(
            text = "Adaryus",
            style = if (compact) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        brush = Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                Color(0xFF39FF14)
                            )
                        )
                    )
                ) {
                    append("Builds Apps")
                }
            },
            style = if (compact) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "AI-powered development, relentless presentation, and a neon-cyber interface that feels pulled straight from your chosen theme.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "This APK showcases business systems, storefronts, media hubs, utilities, learning flows, and secure platforms inside one dramatic Samsung-friendly experience.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoBadge("Neon Cyber")
                InfoBadge("Animated UI")
                InfoBadge("APK Ready")
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoBadge("Neon Cyber")
                InfoBadge("Animated UI")
                InfoBadge("APK Ready")
            }
        }

        Spacer(modifier = Modifier.weight(1f, fill = false))

        Text(
            text = "Built with responsive Compose layouts that stay dramatic without losing usability.",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onExploreCategories,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Explore Categories")
                }
                OutlinedButton(
                    onClick = { onRequestBuild(BuildRequestType.BusinessSuite) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Request A Build")
                }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onExploreCategories,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Explore Categories")
                }
                OutlinedButton(
                    onClick = { onRequestBuild(BuildRequestType.Storefront) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Request A Build")
                }
            }
        }
    }
}

@Composable
private fun HeroPoster(modifier: Modifier, compact: Boolean) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.58f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        PosterArtwork(
            style = PosterStyle.Builder,
            title = "Adaryus Builds Apps",
            badge = "Samsung-ready",
            modifier = Modifier
                .fillMaxWidth()
                .height(if (compact) 176.dp else 238.dp)
        )
    }
}

@Composable
private fun PosterGallerySection(layout: ResponsiveLayout) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Image-Driven Showcase")
        if (layout.wideLayout) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                showcaseCards.forEach { card ->
                    PosterCard(
                        title = card.title,
                        summary = card.summary,
                        tags = card.tags,
                        posterStyle = card.posterStyle,
                        modifier = Modifier.weight(1f),
                        imageHeight = layout.photoHeight + 16.dp
                    )
                }
            }
        } else {
            showcaseCards.forEach { card ->
                PosterCard(
                    title = card.title,
                    summary = card.summary,
                    tags = card.tags,
                    posterStyle = card.posterStyle,
                    modifier = Modifier.fillMaxWidth(),
                    imageHeight = layout.photoHeight + 12.dp
                )
            }
        }
    }
}

@Composable
private fun PosterCard(
    title: String,
    summary: String,
    tags: List<String>,
    posterStyle: PosterStyle,
    modifier: Modifier = Modifier,
    imageHeight: androidx.compose.ui.unit.Dp
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PosterArtwork(
                style = posterStyle,
                title = title,
                badge = tags.firstOrNull() ?: "Feature",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium)
                Text(summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    tags.forEach { tag ->
                        Text(
                            text = tag,
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
private fun QuickActionsSection(
    layout: ResponsiveLayout,
    onExploreCategories: () -> Unit,
    onOpenShowcase: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    SectionCard(title = "Fast Paths") {
        ActionRow(
            left = homeQuickActions[0],
            right = homeQuickActions[1],
            stacked = layout.singleColumn,
            onLeftClick = onExploreCategories,
            onRightClick = onOpenShowcase
        )
        ActionRow(
            left = homeQuickActions[2],
            right = homeQuickActions[3],
            stacked = layout.singleColumn,
            onLeftClick = { onRequestBuild(BuildRequestType.UtilityTool) },
            onRightClick = onOpenShowcase
        )
    }
}

@Composable
private fun StudioHighlightsSection() {
    SectionCard(title = "Why The UI Feels Premium") {
        studioHighlights.forEachIndexed { index, item ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.headline, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(item.detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (index < studioHighlights.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
            }
        }
    }
}
