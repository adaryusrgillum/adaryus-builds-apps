package com.agentic.android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.max

@Composable
internal fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(78.dp)
                        .height(5.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            ),
                            RoundedCornerShape(999.dp)
                        )
                )
                Text(
                    text = "ADARYUS FIRE POND",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
            content()
        }
    }
}

@Composable
internal fun SectionTitle(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
internal fun InfoBadge(text: String) {
    Box(
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.24f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f),
                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.14f)
                    )
                ),
                RoundedCornerShape(999.dp)
            )
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.28f), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
internal fun MetricBadge(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.92f), RoundedCornerShape(18.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f), RoundedCornerShape(18.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
        Text(text = label.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
internal fun ManifestoPhraseWall(
    phrases: List<String>,
    opacity: Float,
    modifier: Modifier = Modifier,
    columns: Int = 6
) {
    val transition = rememberInfiniteTransition(label = "manifesto_wall")
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(columns) { index ->
            val offset = transition.animateFloat(
                initialValue = 0f,
                targetValue = -620f,
                animationSpec = infiniteRepeatable(
                    animation = tween(26000 + index * 3200, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "phrase_column_$index"
            )
            val items = remember(phrases, index) { List(phrases.size) { phrases[(it + index * 3) % phrases.size] } }
            val color = when (index % 3) {
                0 -> MaterialTheme.colorScheme.primary.copy(alpha = opacity)
                1 -> MaterialTheme.colorScheme.secondary.copy(alpha = opacity * 0.92f)
                else -> MaterialTheme.colorScheme.tertiary.copy(alpha = opacity * 0.86f)
            }
            Column(
                modifier = Modifier.weight(1f).graphicsLayer { translationY = offset.value },
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                repeat(2) {
                    items.forEach { phrase ->
                        Text(
                            text = phrase,
                            color = color,
                            fontSize = 11.sp,
                            letterSpacing = 0.8.sp,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
            }
        }
    }
}

private data class KoiFishProfile(
    val band: KoiDepthBand,
    val startX: Float,
    val startY: Float,
    val controlX: Float,
    val controlY: Float,
    val endX: Float,
    val endY: Float,
    val durationMillis: Int,
    val delayMillis: Int,
    val bodyColors: List<Color>,
    val spotColor: Color
)

@Composable
internal fun KoiScene(spec: KoiSceneSpec, modifier: Modifier = Modifier) {
    val profiles = remember(spec) { buildKoiProfiles(spec) }
    val transition = rememberInfiniteTransition(label = "koi_scene")
    val rippleShift = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(20000, easing = LinearEasing), repeatMode = RepeatMode.Restart),
        label = "ripple_shift"
    )
    val fishProgress = profiles.mapIndexed { index, profile ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = profile.durationMillis, delayMillis = profile.delayMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "koi_progress_$index"
        )
    }

    Canvas(modifier = modifier) {
        drawRippleField(rippleShift.value, spec.rippleIntensity)
        profiles.forEachIndexed { index, profile -> drawKoiFish(profile, fishProgress[index].value) }
    }
}

private fun buildKoiProfiles(spec: KoiSceneSpec): List<KoiFishProfile> {
    val palettes = listOf(
        listOf(Color(0xFFF6F0E8), Color(0xFFF08A3E), Color(0xFFB9281A)),
        listOf(Color(0xFFF6EBD7), Color(0xFFFFA63C), Color(0xFFD4481E)),
        listOf(Color(0xFFEDE7DE), Color(0xFFE25829), Color(0xFF8C1D13)),
        listOf(Color(0xFFF7F3EA), Color(0xFFFFC24B), Color(0xFFE45B2A))
    )
    return List(spec.fishCount) { index ->
        val band = spec.depthBands[index % spec.depthBands.size]
        val forward = index % 2 == 0
        val palette = palettes[index % palettes.size]
        val duration = spec.speedRange.first + ((index * 1733) % max(1, spec.speedRange.last - spec.speedRange.first + 1))
        KoiFishProfile(
            band = band,
            startX = if (forward) -0.18f else 1.18f,
            startY = 0.16f + ((index * 17) % 58) / 100f,
            controlX = 0.26f + ((index * 11) % 46) / 100f,
            controlY = 0.18f + ((index * 13) % 62) / 100f,
            endX = if (forward) 1.18f else -0.18f,
            endY = 0.18f + ((index * 9 + 23) % 60) / 100f,
            durationMillis = max(12000, (duration / band.speedMultiplier).toInt()),
            delayMillis = index * 1200,
            bodyColors = palette,
            spotColor = if (index % 2 == 0) Color(0xFF2C110C) else Color(0xFFFDE6B0)
        )
    }
}

private fun quadraticPoint(t: Float, p0: Offset, p1: Offset, p2: Offset): Offset {
    val oneMinusT = 1f - t
    return Offset(
        (oneMinusT * oneMinusT * p0.x) + (2f * oneMinusT * t * p1.x) + (t * t * p2.x),
        (oneMinusT * oneMinusT * p0.y) + (2f * oneMinusT * t * p1.y) + (t * t * p2.y)
    )
}

private fun quadraticTangent(t: Float, p0: Offset, p1: Offset, p2: Offset): Offset {
    return Offset(
        2f * (1f - t) * (p1.x - p0.x) + 2f * t * (p2.x - p1.x),
        2f * (1f - t) * (p1.y - p0.y) + 2f * t * (p2.y - p1.y)
    )
}

private fun DrawScope.drawRippleField(progress: Float, intensity: Float) {
    drawCircle(
        brush = Brush.radialGradient(listOf(Color(0xFFFF9A3C).copy(alpha = intensity * 0.35f), Color.Transparent)),
        radius = size.minDimension * 0.26f,
        center = Offset(size.width * (0.18f + progress * 0.08f), size.height * 0.28f)
    )
    drawCircle(
        brush = Brush.radialGradient(listOf(Color(0xFFE33A24).copy(alpha = intensity * 0.28f), Color.Transparent)),
        radius = size.minDimension * 0.22f,
        center = Offset(size.width * (0.78f - progress * 0.10f), size.height * 0.62f)
    )
    repeat(3) { index ->
        drawCircle(
            color = Color.White.copy(alpha = intensity * 0.16f),
            radius = size.minDimension * (0.07f + index * 0.018f),
            center = Offset(size.width * (0.22f + index * 0.26f), size.height * (0.22f + ((progress + index * 0.11f) % 0.4f))),
            style = Stroke(width = 1.8f + index)
        )
    }
    drawLine(
        color = Color(0xFFFFC24B).copy(alpha = intensity * 0.28f),
        start = Offset(size.width * 0.08f, size.height * 0.84f),
        end = Offset(size.width * 0.72f, size.height * 0.18f),
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawKoiFish(profile: KoiFishProfile, progress: Float) {
    val p0 = Offset(size.width * profile.startX, size.height * profile.startY)
    val p1 = Offset(size.width * profile.controlX, size.height * profile.controlY)
    val p2 = Offset(size.width * profile.endX, size.height * profile.endY)
    val point = quadraticPoint(progress, p0, p1, p2)
    val tangent = quadraticTangent(progress, p0, p1, p2)
    val angle = atan2(tangent.y, tangent.x) * (180f / PI.toFloat())
    val fishLength = size.minDimension * 0.16f * profile.band.sizeScale
    val fishHeight = fishLength * 0.34f

    translate(point.x, point.y) {
        rotate(angle) {
            drawOval(brush = Brush.linearGradient(profile.bodyColors), topLeft = Offset(-fishLength * 0.46f, -fishHeight * 0.36f), size = Size(fishLength * 0.78f, fishHeight * 0.72f), alpha = profile.band.alpha)
            drawOval(color = Color.White.copy(alpha = 0.12f * profile.band.alpha), topLeft = Offset(-fishLength * 0.24f, -fishHeight * 0.26f), size = Size(fishLength * 0.30f, fishHeight * 0.22f))
            val tail = Path().apply {
                moveTo(fishLength * 0.24f, 0f)
                lineTo(fishLength * 0.48f, -fishHeight * 0.46f)
                lineTo(fishLength * 0.42f, 0f)
                lineTo(fishLength * 0.48f, fishHeight * 0.46f)
                close()
            }
            drawPath(path = tail, brush = Brush.linearGradient(listOf(profile.bodyColors[1], profile.bodyColors.last())), alpha = profile.band.alpha * 0.96f)
            repeat(3) { spotIndex ->
                drawCircle(
                    color = profile.spotColor.copy(alpha = profile.band.alpha * (0.30f + spotIndex * 0.12f)),
                    radius = fishHeight * (0.10f + spotIndex * 0.02f),
                    center = Offset(-fishLength * (0.18f - spotIndex * 0.14f), if (spotIndex % 2 == 0) -fishHeight * 0.08f else fishHeight * 0.05f)
                )
            }
            drawCircle(color = Color(0xFF140804).copy(alpha = profile.band.alpha), radius = fishHeight * 0.06f, center = Offset(-fishLength * 0.28f, -fishHeight * 0.05f))
        }
    }
}

private data class PosterPalette(
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val accent: Color,
    val accentSoft: Color,
    val card: Color,
    val text: Color
)

@Composable
internal fun PosterArtwork(
    style: PosterStyle,
    title: String,
    badge: String,
    modifier: Modifier = Modifier
) {
    val palette = posterPalette(style)
    Box(
        modifier = modifier
            .background(Brush.linearGradient(listOf(palette.backgroundTop, palette.backgroundBottom)), RoundedCornerShape(22.dp))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(108.dp)
                .background(
                    Brush.radialGradient(listOf(palette.accent.copy(alpha = 0.30f), Color.Transparent)),
                    CircleShape
                )
        )

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PosterPill("ADARYUS", palette.accent, palette.text)
                PosterPill(badge.uppercase(), palette.card, palette.text)
            }
            PosterLayout(style = style, palette = palette)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = palette.text
                )
                Text(
                    text = posterDescription(style),
                    style = MaterialTheme.typography.bodySmall,
                    color = palette.text.copy(alpha = 0.72f)
                )
            }
        }
    }
}

@Composable
private fun PosterPill(text: String, container: Color, content: Color) {
    Box(
        modifier = Modifier
            .background(container, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = content)
    }
}

@Composable
private fun PosterLayout(style: PosterStyle, palette: PosterPalette) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        when (style) {
            PosterStyle.Community -> {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    repeat(3) { index ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(palette.card, RoundedCornerShape(16.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(if (index == 1) palette.accent else palette.accentSoft, CircleShape)
                            )
                            PosterLine(1f, palette.text.copy(alpha = 0.20f))
                            PosterLine(0.56f, palette.text.copy(alpha = 0.12f))
                        }
                    }
                }
                PosterLaneRow(listOf(1f, 1f, 1f, 1f), palette)
            }

            PosterStyle.Productivity -> {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    PosterMetricCard(Modifier.weight(1f), "TASKS", "48", palette)
                    PosterMetricCard(Modifier.weight(1f), "QUEUES", "12", palette)
                }
                PosterBarChart(listOf(0.38f, 0.74f, 0.56f, 0.88f, 0.46f), palette)
                PosterLaneRow(listOf(1f, 1f, 1f), palette, highlightIndex = 1)
            }

            PosterStyle.Entertainment -> {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(0.42f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(4) { index ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp + (index * 3).dp)
                                    .background(if (index == 0) palette.accent.copy(alpha = 0.86f) else palette.card, RoundedCornerShape(12.dp))
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.58f)
                            .height(118.dp)
                            .background(palette.card, RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier.size(46.dp).background(palette.accent, CircleShape))
                        Canvas(modifier = Modifier.size(16.dp, 18.dp)) {
                            val playTriangle = Path().apply {
                                moveTo(0f, 0f)
                                lineTo(0f, size.height)
                                lineTo(size.width, size.height / 2f)
                                close()
                            }
                            drawPath(playTriangle, color = palette.text)
                        }
                    }
                }
                PosterBarChart(listOf(0.44f, 0.64f, 0.54f, 0.74f, 0.40f), palette)
            }

            PosterStyle.Finance -> {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    PosterMetricCard(Modifier.weight(1f), "BALANCE", "$48K", palette)
                    PosterMetricCard(Modifier.weight(1f), "SECURE", "99.9%", palette)
                }
                PosterStack(palette)
                PosterLaneRow(listOf(1f, 1f, 1f, 1f), palette, highlightIndex = 3)
            }

            PosterStyle.Education -> {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    repeat(3) { index ->
                        PosterMetricCard(Modifier.weight(1f), "STEP ${index + 1}", if (index == 2) "DONE" else "LIVE", palette)
                    }
                }
                PosterProgressTrack(palette)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1.3f).height(40.dp).background(palette.card, RoundedCornerShape(14.dp)))
                    Box(modifier = Modifier.weight(0.7f).height(40.dp).background(palette.accent.copy(alpha = 0.85f), RoundedCornerShape(14.dp)))
                }
            }

            PosterStyle.Utility -> {
                PosterRouteMap(palette)
                PosterLaneRow(listOf(1f, 1f, 1f), palette, highlightIndex = 0)
                PosterBarChart(listOf(0.50f, 0.80f, 0.62f, 0.92f), palette)
            }
        }
    }
}

private fun posterDescription(style: PosterStyle): String = when (style) {
    PosterStyle.Community -> "Conversation-led feeds, profile motion, and member spaces"
    PosterStyle.Productivity -> "Dashboards, approvals, queues, and operational rhythm"
    PosterStyle.Entertainment -> "Media rails, featured playback, and cinematic browsing"
    PosterStyle.Finance -> "Trust-focused balance boards, security states, and clarity"
    PosterStyle.Education -> "Lesson boards, progress steps, and guided learning flow"
    PosterStyle.Utility -> "Maps, quick actions, tools, and everyday utility flow"
}

@Composable
private fun PosterLine(widthFraction: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .height(8.dp)
            .background(color, RoundedCornerShape(999.dp))
    )
}

@Composable
private fun PosterLaneRow(widths: List<Float>, palette: PosterPalette, highlightIndex: Int = 1) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        widths.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .background(
                        if (index == highlightIndex) palette.accent.copy(alpha = 0.85f) else palette.card,
                        RoundedCornerShape(14.dp)
                    )
            )
        }
    }
}

@Composable
private fun PosterBarChart(heights: List<Float>, palette: PosterPalette) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(palette.card, RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            heights.forEach { heightFraction ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(heightFraction)
                        .background(
                            if (heightFraction > 0.8f) palette.accent else palette.accentSoft,
                            RoundedCornerShape(999.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun PosterStack(palette: PosterPalette) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(palette.card, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PosterLine(0.92f, palette.accent.copy(alpha = 0.78f))
            PosterLine(0.58f, palette.accentSoft)
        }
    }
}

@Composable
private fun PosterProgressTrack(palette: PosterPalette) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(palette.card, RoundedCornerShape(18.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(10.dp)
                        .background(
                            if (index < 3) palette.accent else palette.accentSoft,
                            RoundedCornerShape(999.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun PosterRouteMap(palette: PosterPalette) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(palette.card, RoundedCornerShape(18.dp))
            .padding(12.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(size.width * 0.08f, size.height * 0.72f)
                quadraticTo(size.width * 0.36f, size.height * 0.18f, size.width * 0.58f, size.height * 0.42f)
                quadraticTo(size.width * 0.78f, size.height * 0.62f, size.width * 0.94f, size.height * 0.18f)
            }
            drawPath(path = path, color = palette.accent, style = Stroke(width = 10f, cap = StrokeCap.Round))
        }
    }
}

@Composable
private fun PosterMetricCard(modifier: Modifier, title: String, value: String, palette: PosterPalette) {
    Column(
        modifier = modifier
            .background(palette.card, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.labelMedium, color = palette.text.copy(alpha = 0.68f))
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = palette.text)
    }
}

private fun posterPalette(style: PosterStyle): PosterPalette = when (style) {
    PosterStyle.Community -> PosterPalette(Color(0xFF100C0B), Color(0xFF35140E), Color(0xFFF36A2B), Color(0xFF663226), Color(0xFF1A1110), Color(0xFFF6F1EA))
    PosterStyle.Productivity -> PosterPalette(Color(0xFF0C0A0A), Color(0xFF2A0D0B), Color(0xFFE1472D), Color(0xFF6A2418), Color(0xFF171010), Color(0xFFF7F2EC))
    PosterStyle.Entertainment -> PosterPalette(Color(0xFF120C08), Color(0xFF3B1809), Color(0xFFFFA135), Color(0xFF6E3A17), Color(0xFF1C120E), Color(0xFFFFF7EE))
    PosterStyle.Finance -> PosterPalette(Color(0xFF0A0807), Color(0xFF2C1107), Color(0xFFFFC14A), Color(0xFF705221), Color(0xFF17110E), Color(0xFFFAF3E8))
    PosterStyle.Education -> PosterPalette(Color(0xFF100908), Color(0xFF31160C), Color(0xFFFF8C2F), Color(0xFF6E3114), Color(0xFF1C110D), Color(0xFFFFF3E6))
    PosterStyle.Utility -> PosterPalette(Color(0xFF0B0909), Color(0xFF24110B), Color(0xFFE76A25), Color(0xFF5A2A18), Color(0xFF181111), Color(0xFFF8F1EA))
}

@Composable
internal fun ResponsivePair(
    stacked: Boolean,
    first: @Composable (Modifier) -> Unit,
    second: @Composable (Modifier) -> Unit
) {
    if (stacked) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            first(Modifier.fillMaxWidth())
            second(Modifier.fillMaxWidth())
        }
    } else {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            first(Modifier.weight(1f))
            second(Modifier.weight(1f))
        }
    }
}

@Composable
internal fun ActionCard(action: ActionItem, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.92f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        Brush.linearGradient(listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.secondary.copy(alpha = 0.82f))),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(action.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
            }
            Text(action.title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text(action.summary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) { Text(action.buttonLabel) }
        }
    }
}

@Composable
internal fun ActionRow(left: ActionItem, right: ActionItem, stacked: Boolean, onLeftClick: () -> Unit, onRightClick: () -> Unit) {
    ResponsivePair(
        stacked = stacked,
        first = { modifier -> ActionCard(left, modifier, onLeftClick) },
        second = { modifier -> ActionCard(right, modifier, onRightClick) }
    )
}

@Composable
internal fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
internal fun PortalWebView(url: String, height: Dp) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(height),
            factory = { context ->
                WebView(context).apply {
                    CookieManager.getInstance().setAcceptCookie(true)
                    CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadsImagesAutomatically = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = { webView ->
                if (webView.url != url) webView.loadUrl(url)
            }
        )
    }
}

internal fun launchUriIntent(context: Context, target: String) {
    val intent = when {
        target.startsWith("mailto:") -> Intent(Intent.ACTION_SENDTO, Uri.parse(target))
        target.startsWith("tel:") -> Intent(Intent.ACTION_DIAL, Uri.parse(target))
        target.startsWith("geo:") -> Intent(Intent.ACTION_VIEW, Uri.parse(target))
        else -> Intent(Intent.ACTION_VIEW, Uri.parse(target))
    }

    runCatching { context.startActivity(intent) }
        .recoverCatching { context.startActivity(Intent.createChooser(intent, "Open with")) }
        .getOrElse {
            if (it !is ActivityNotFoundException) throw it
        }
}

internal fun launchEmailDraft(context: Context, subject: String, body: String, recipient: String = StudioEmail): Boolean {
    val mailtoIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$recipient")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    return runCatching {
        context.startActivity(mailtoIntent)
        true
    }.recoverCatching {
        val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        context.startActivity(Intent.createChooser(fallbackIntent, "Send build brief"))
        true
    }.getOrElse { false }
}
