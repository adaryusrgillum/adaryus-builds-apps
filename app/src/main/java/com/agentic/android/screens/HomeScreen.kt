package com.agentic.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agentic.android.ActionRow
import com.agentic.android.BuildRequestType
import com.agentic.android.InfoBadge
import com.agentic.android.KoiScene
import com.agentic.android.MetricBadge
import com.agentic.android.PosterArtwork
import com.agentic.android.PosterStyle
import com.agentic.android.ResponsiveLayout
import com.agentic.android.SectionCard
import com.agentic.android.SectionTitle
import com.agentic.android.heroKoiSpec
import com.agentic.android.homeQuickActions
import com.agentic.android.showcaseCards
import com.agentic.android.studioHighlights

@Composable
internal fun HomeScreen(
    layout: ResponsiveLayout,
    onExploreCategories: () -> Unit,
    onOpenGuide: () -> Unit,
    onOpenShowcase: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeHeroSection(layout, onExploreCategories, onOpenGuide, onRequestBuild)
        PosterGallerySection(layout)
        QuickActionsSection(layout, onExploreCategories, onOpenGuide, onOpenShowcase, onRequestBuild)
        StudioHighlightsSection()
    }
}

@Composable
private fun HomeHeroSection(
    layout: ResponsiveLayout,
    onExploreCategories: () -> Unit,
    onOpenGuide: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = layout.heroHeight)
        ) {
            KoiScene(spec = heroKoiSpec, modifier = Modifier.matchParentSize())
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.background.copy(alpha = 0.58f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.74f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)
                            )
                        )
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
                        onOpenGuide = onOpenGuide,
                        onRequestBuild = onRequestBuild
                    )
                    HeroPoster(modifier = Modifier.weight(0.9f), compact = layout.compactWidth)
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
                        onOpenGuide = onOpenGuide,
                        onRequestBuild = onRequestBuild
                    )
                    HeroPoster(modifier = Modifier.fillMaxWidth(), compact = layout.compactWidth)
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
    onOpenGuide: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "AI • MARKETING • DEVELOPMENT • SCALE",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
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
                        brush = Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.tertiary
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
            text = "A premium fire-lit studio showcase with subtle koi movement, a manifesto wall in the atmosphere, and a clear path from idea to installable Android product.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Explore functional app categories, compare build approaches, review cinematic boards, and turn the strongest direction into a production-ready brief.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoBadge("Fire Palette")
                InfoBadge("Realistic Koi Motion")
                InfoBadge("Samsung-ready")
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoBadge("Fire Palette")
                InfoBadge("Realistic Koi Motion")
                InfoBadge("Samsung-ready")
            }
        }

        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MetricBadge(value = "06", label = "Categories", modifier = Modifier.fillMaxWidth())
                MetricBadge(value = "05", label = "App Types", modifier = Modifier.fillMaxWidth())
                MetricBadge(value = "APK", label = "Release Ready", modifier = Modifier.fillMaxWidth())
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MetricBadge(value = "06", label = "Categories", modifier = Modifier.weight(1f))
                MetricBadge(value = "05", label = "App Types", modifier = Modifier.weight(1f))
                MetricBadge(value = "APK", label = "Release Ready", modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

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
                OutlinedButton(onClick = onOpenGuide, modifier = Modifier.fillMaxWidth()) {
                    Text("Open App Types Guide")
                }
                OutlinedButton(onClick = { onRequestBuild(BuildRequestType.ProductivityBusiness) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Start Build Brief")
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
                OutlinedButton(onClick = onOpenGuide, modifier = Modifier.weight(1f)) {
                    Text("App Types Guide")
                }
                OutlinedButton(
                    onClick = { onRequestBuild(BuildRequestType.ProductivityBusiness) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Build Brief")
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
            style = PosterStyle.Productivity,
            title = "Adaryus Builds Apps",
            badge = "Fire Koi",
            modifier = Modifier
                .fillMaxWidth()
                .height(if (compact) 176.dp else 238.dp)
        )
    }
}

@Composable
private fun PosterGallerySection(layout: ResponsiveLayout) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Showcase Boards")
        if (layout.wideLayout) {
            showcaseCards.chunked(2).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { card ->
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
                badge = tags.firstOrNull() ?: "Board",
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
                    tags.forEach { tag -> InfoBadge(tag) }
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
    onOpenGuide: () -> Unit,
    onOpenShowcase: () -> Unit,
    onRequestBuild: (BuildRequestType) -> Unit
) {
    SectionCard(title = "Fast Paths") {
        ActionRow(
            left = homeQuickActions[0],
            right = homeQuickActions[1],
            stacked = layout.singleColumn,
            onLeftClick = onExploreCategories,
            onRightClick = onOpenGuide
        )
        ActionRow(
            left = homeQuickActions[2],
            right = homeQuickActions[3],
            stacked = layout.singleColumn,
            onLeftClick = onOpenShowcase,
            onRightClick = { onRequestBuild(BuildRequestType.UtilitiesNavigation) }
        )
    }
}

@Composable
private fun StudioHighlightsSection() {
    SectionCard(title = "Why The Experience Feels Premium") {
        studioHighlights.forEach { item ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.headline, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(item.detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
