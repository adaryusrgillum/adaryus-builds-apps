package com.agentic.android.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agentic.android.BuildRequestType
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
        SectionTitle("Showcase And Motion")

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(650))
        ) {
            MotionDeckSection(onOpenRequest)
        }

        ShowcaseCardsSection(layout, onOpenRequest)
        BuildPhasesSection(onOpenRequest)
    }
}

@Composable
private fun MotionDeckSection(onOpenRequest: (BuildRequestType) -> Unit) {
    val transition = rememberInfiniteTransition(label = "motion_deck")
    val scanOffset = transition.animateFloat(
        initialValue = -120f,
        targetValue = 320f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan_offset"
    )
    val barScale = transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar_scale"
    )

    SectionCard(title = "Animated Motion Deck") {
        Text(
            text = "This screen leans into motion so the APK feels alive on Samsung hardware instead of static. A moving scan line, pulsing signal bars, and high-contrast posters give the showcase a premium presentation.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(228.dp)
        ) {
            PosterArtwork(
                style = com.agentic.android.PosterStyle.Motion,
                title = "Animated Motion Deck",
                badge = "Live",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(228.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .graphicsLayer {
                        translationY = scanOffset.value
                        alpha = 0.75f
                    }
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0f),
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(width = 22.dp, height = (42 + index * 12).dp)
                            .graphicsLayer {
                                scaleY = if (index % 2 == 0) barScale.value else 1.2f - (barScale.value - 0.2f)
                            }
                            .background(
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                                RoundedCornerShape(999.dp)
                            )
                    )
                }
            }
        }

        Button(onClick = { onOpenRequest(BuildRequestType.MediaHub) }, modifier = Modifier.fillMaxWidth()) {
            Text("Turn Motion Into A Media Build")
        }
    }
}

@Composable
private fun ShowcaseCardsSection(
    layout: ResponsiveLayout,
    onOpenRequest: (BuildRequestType) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Featured Build Boards")
        if (layout.wideLayout) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                showcaseCards.forEachIndexed { index, card ->
                    ShowcaseCard(card, Modifier.weight(1f), onClick = {
                        val type = if (index == 0) BuildRequestType.BusinessSuite else if (index == 1) BuildRequestType.Storefront else BuildRequestType.MediaHub
                        onOpenRequest(type)
                    })
                }
            }
        } else {
            showcaseCards.forEachIndexed { index, card ->
                ShowcaseCard(card, Modifier.fillMaxWidth(), onClick = {
                    val type = if (index == 0) BuildRequestType.BusinessSuite else if (index == 1) BuildRequestType.Storefront else BuildRequestType.MediaHub
                    onOpenRequest(type)
                })
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
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
                item.tags.forEach { tag ->
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
            Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Text("Use This As The App Direction")
            }
        }
    }
}

@Composable
private fun BuildPhasesSection(onOpenRequest: (BuildRequestType) -> Unit) {
    SectionCard(title = "Build Rhythm") {
        buildPhases.forEachIndexed { index, phase ->
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
            if (index < buildPhases.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
            }
        }
        Button(onClick = { onOpenRequest(BuildRequestType.SecurePlatform) }, modifier = Modifier.fillMaxWidth()) {
            Text("Start A Secure Platform Brief")
        }
    }
}
