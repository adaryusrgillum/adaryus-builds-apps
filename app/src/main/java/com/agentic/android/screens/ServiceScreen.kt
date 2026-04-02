package com.agentic.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agentic.android.BuildRequestType
import com.agentic.android.InfoBadge
import com.agentic.android.PosterArtwork
import com.agentic.android.ResponsiveLayout
import com.agentic.android.SectionCard
import com.agentic.android.SectionTitle
import com.agentic.android.buildPhases
import com.agentic.android.showcaseCards

@Composable
internal fun ShowcaseScreen(
    layout: ResponsiveLayout,
    onOpenRequest: (BuildRequestType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ShowcaseHero(layout, onOpenRequest)
        ShowcaseCardsSection(layout, onOpenRequest)
        BuildPhasesSection(onOpenRequest)
    }
}

@Composable
private fun ShowcaseHero(layout: ResponsiveLayout, onOpenRequest: (BuildRequestType) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = if (layout.wideLayout) 250.dp else 230.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.94f),
                                MaterialTheme.colorScheme.background.copy(alpha = 0.90f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 18.dp, end = 18.dp)
                    .size(160.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f), Color.Transparent)
                        ),
                        RoundedCornerShape(999.dp)
                    )
            )
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "SHOWCASE",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Beautiful editorial boards for business, media, finance, and utility directions.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "These boards show how the same calmer premium visual system can flex across different product goals without losing clarity or authority.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    InfoBadge("Cleaner hierarchy")
                    InfoBadge("Premium materials")
                    InfoBadge("Brief-ready concepts")
                }
                Button(onClick = { onOpenRequest(BuildRequestType.ProductivityBusiness) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Start With A Showcase Direction")
                }
            }
        }
    }
}

@Composable
private fun ShowcaseCardsSection(layout: ResponsiveLayout, onOpenRequest: (BuildRequestType) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Featured Build Boards")
        if (layout.wideLayout) {
            showcaseCards.chunked(2).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { card ->
                        ShowcaseCard(item = card, modifier = Modifier.weight(1f), onClick = { onOpenRequest(card.requestType) })
                    }
                }
            }
        } else {
            showcaseCards.forEach { card ->
                ShowcaseCard(item = card, modifier = Modifier.fillMaxWidth(), onClick = { onOpenRequest(card.requestType) })
            }
        }
    }
}

@Composable
private fun ShowcaseCard(
    item: com.agentic.android.ShowcaseCard,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PosterArtwork(
                style = item.posterStyle,
                title = item.title,
                badge = item.tags.firstOrNull() ?: "Board",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Text(item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(item.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                item.tags.forEach { tag -> InfoBadge(tag) }
            }
            Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Text("Use This Direction")
            }
        }
    }
}

@Composable
private fun BuildPhasesSection(onOpenRequest: (BuildRequestType) -> Unit) {
    SectionCard(title = "Build Rhythm") {
        buildPhases.forEach { phase ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(phase.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
                Column {
                    Text(phase.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(phase.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        Button(onClick = { onOpenRequest(BuildRequestType.EntertainmentStreaming) }, modifier = Modifier.fillMaxWidth()) {
            Text("Turn This Into A Build Brief")
        }
    }
}
