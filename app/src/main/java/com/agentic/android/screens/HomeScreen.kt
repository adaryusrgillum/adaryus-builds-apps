package com.agentic.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agentic.android.ActionRow
import com.agentic.android.AiUpdate
import com.agentic.android.BuildRequestType
import com.agentic.android.InfoBadge
import com.agentic.android.MetricBadge
import com.agentic.android.PosterArtwork
import com.agentic.android.PosterStyle
import com.agentic.android.ResponsiveLayout
import com.agentic.android.SalesOutcome
import com.agentic.android.SectionCard
import com.agentic.android.SectionTitle
import com.agentic.android.TrustSignal
import com.agentic.android.aiUpdates
import com.agentic.android.homeQuickActions
import com.agentic.android.salesOutcomes
import com.agentic.android.showcaseCards
import com.agentic.android.studioHighlights
import com.agentic.android.trustSignals

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
        SalesOutcomeSection(layout)
        AiUpdatesSection(layout)
        PosterGallerySection(layout)
        QuickActionsSection(layout, onExploreCategories, onOpenGuide, onOpenShowcase, onRequestBuild)
        TrustSection()
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
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.96f),
                                MaterialTheme.colorScheme.background.copy(alpha = 0.92f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 24.dp, end = 22.dp)
                    .height(160.dp)
                    .fillMaxWidth(0.48f)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.28f),
                                Color.Transparent
                            )
                        ),
                        RoundedCornerShape(999.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 18.dp, bottom = 18.dp)
                    .height(110.dp)
                    .fillMaxWidth(0.40f)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                                Color.Transparent
                            )
                        ),
                        RoundedCornerShape(999.dp)
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

@OptIn(ExperimentalLayoutApi::class)
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
            text = "EDITORIAL • TRUST • CONVERSION • SCALE",
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
            text = "Beautiful mobile and web experiences built to help you capture more leads, close more buyers, and feel unmistakably premium at first glance.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "This version shifts the brand into a softer editorial luxury direction with cleaner materials, stronger typography, calmer color, and a buyer journey designed to feel expensive in the best way.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoBadge("Lead capture ready")
            InfoBadge("Editorial premium design")
            InfoBadge("AI-ready product strategy")
            InfoBadge("Samsung-ready delivery")
            InfoBadge("Buyer-focused messaging")
        }

        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MetricBadge(value = "Leads", label = "Focused", modifier = Modifier.fillMaxWidth())
                MetricBadge(value = "Offers", label = "Clear", modifier = Modifier.fillMaxWidth())
                MetricBadge(value = "APK", label = "Ready", modifier = Modifier.fillMaxWidth())
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MetricBadge(value = "Leads", label = "Focused", modifier = Modifier.weight(1f))
                MetricBadge(value = "Offers", label = "Clear", modifier = Modifier.weight(1f))
                MetricBadge(value = "APK", label = "Ready", modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { onRequestBuild(BuildRequestType.ProductivityBusiness) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Start Sales-Focused Brief")
                }
                OutlinedButton(onClick = onExploreCategories, modifier = Modifier.fillMaxWidth()) {
                    Text("Browse Revenue-Ready Categories")
                }
                OutlinedButton(onClick = onOpenGuide, modifier = Modifier.fillMaxWidth()) {
                    Text("Choose The Build Path")
                }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onRequestBuild(BuildRequestType.ProductivityBusiness) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Start Sales-Focused Brief")
                }
                OutlinedButton(onClick = onExploreCategories, modifier = Modifier.weight(1f)) {
                    Text("Revenue-Ready Categories")
                }
                OutlinedButton(onClick = onOpenGuide, modifier = Modifier.weight(1f)) {
                    Text("Build Path Guide")
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
            title = "Beautiful Apps That Convert",
            badge = "Editorial",
            modifier = Modifier
                .fillMaxWidth()
                .height(if (compact) 176.dp else 238.dp)
        )
    }
}

@Composable
private fun SalesOutcomeSection(layout: ResponsiveLayout) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Built To Support Sales")
        if (layout.wideLayout) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                salesOutcomes.forEach { outcome ->
                    SalesOutcomeCard(outcome, Modifier.weight(1f))
                }
            }
        } else {
            salesOutcomes.forEach { outcome ->
                SalesOutcomeCard(outcome, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun AiUpdatesSection(layout: ResponsiveLayout) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("AI That Actually Matters Right Now")
        Text(
            text = "These are the AI shifts worth paying attention to if the goal is a smarter product, a more defensible workflow, or a more modern service offer.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (layout.wideLayout) {
            aiUpdates.chunked(2).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        AiUpdateCard(item, Modifier.weight(1f))
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        } else {
            aiUpdates.forEach { item ->
                AiUpdateCard(item, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun AiUpdateCard(update: AiUpdate, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.22f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                            )
                        ),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(12.dp)
            ) {
                Icon(update.icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            }
            Text(
                text = update.sourceLabel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.2.sp
            )
            Text(update.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(update.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun SalesOutcomeCard(outcome: SalesOutcome, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.14f)
                            )
                        ),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(12.dp)
            ) {
                Icon(outcome.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Text(outcome.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(outcome.detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun PosterGallerySection(layout: ResponsiveLayout) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Professional Showcase Boards")
        Text(
            text = "These concepts are framed to feel more buyer-ready: clearer offers, stronger hierarchy, and more trust on first look.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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

@OptIn(ExperimentalLayoutApi::class)
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
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
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
    SectionCard(title = "Next Best Actions") {
        Text(
            text = "Good sales apps are not just attractive. They create a clear offer, a reason to trust you, and one strong next step.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
            onRightClick = { onRequestBuild(BuildRequestType.ProductivityBusiness) }
        )
    }
}

@Composable
private fun TrustSection() {
    SectionCard(title = "Why Buyers Will Take It More Seriously") {
        trustSignals.forEach { signal ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(signal.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(signal.detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun StudioHighlightsSection() {
    SectionCard(title = "Professional Positioning Upgrades") {
        studioHighlights.forEach { item ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.headline, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(item.detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
