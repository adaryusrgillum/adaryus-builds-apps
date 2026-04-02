package com.agentic.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.agentic.android.AppTechnologyType
import com.agentic.android.BuildRequestType
import com.agentic.android.InfoBadge
import com.agentic.android.MetricBadge
import com.agentic.android.PosterArtwork
import com.agentic.android.ResponsiveLayout
import com.agentic.android.SectionCard
import com.agentic.android.SectionTitle
import com.agentic.android.appTechnologyTypes

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TechnologyGuideScreen(
    layout: ResponsiveLayout,
    onOpenRequest: (BuildRequestType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GuideHero(layout)
        SectionTitle("Technology Paths")
        appTechnologyTypes.forEach { type ->
            TechnologyTypeCard(type = type, onOpenRequest = onOpenRequest)
        }
    }
}

@Composable
private fun GuideHero(layout: ResponsiveLayout) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = if (layout.wideLayout) 250.dp else 220.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.94f),
                                MaterialTheme.colorScheme.background.copy(alpha = 0.92f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 18.dp, end = 18.dp)
                    .size(140.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.20f), Color.Transparent)
                        ),
                        RoundedCornerShape(999.dp)
                    )
            )
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "APP TYPES GUIDE",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Choose the right build approach before you invest in the final product.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "This screen explains native, web, hybrid, progressive web, and cross-platform paths so the brand direction stays beautiful while the budget and delivery plan stay sensible.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (layout.singleColumn) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        MetricBadge(value = "05", label = "Build Types", modifier = Modifier.fillMaxWidth())
                        MetricBadge(value = "Guide", label = "Structured", modifier = Modifier.fillMaxWidth())
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        MetricBadge(value = "05", label = "Build Types", modifier = Modifier.weight(1f))
                        MetricBadge(value = "Guide", label = "Structured", modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun TechnologyTypeCard(
    type: AppTechnologyType,
    onOpenRequest: (BuildRequestType) -> Unit
) {
    SectionCard(title = type.title) {
        PosterArtwork(
            style = type.posterStyle,
            title = type.title,
            badge = type.accentLabel,
            modifier = Modifier
                .fillMaxWidth()
                .height(172.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(type.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(type.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                InfoBadge(type.accentLabel)
            }
        }

        GuideList("Strengths", type.strengths)
        GuideList("Tradeoffs", type.tradeoffs)
        GuideList("Best For", type.bestFor)

        Button(onClick = { onOpenRequest(type.recommendedRequestType) }, modifier = Modifier.fillMaxWidth()) {
            Text("Use This Direction In My Brief")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GuideList(title: String, items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                InfoBadge(item)
            }
        }
    }
}
