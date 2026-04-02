package com.agentic.android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(76.dp)
                    .height(5.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        androidx.compose.foundation.shape.RoundedCornerShape(999.dp)
                    )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
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
                MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                androidx.compose.foundation.shape.RoundedCornerShape(999.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface)
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
            .background(
                Brush.linearGradient(
                    listOf(
                        palette.backgroundTop,
                        palette.backgroundBottom
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(104.dp)
                .background(
                    Brush.radialGradient(
                        listOf(
                            palette.accent.copy(alpha = 0.30f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PosterPill("ADARYUS", palette.accent, palette.text)
                PosterPill(badge.uppercase(), palette.card, palette.text)
            }

            when (style) {
                PosterStyle.Builder -> BuilderPosterLayout(palette)
                PosterStyle.Catalog -> CatalogPosterLayout(palette)
                PosterStyle.Motion -> MotionPosterLayout(palette)
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = palette.text
                )
                Text(
                    text = when (style) {
                        PosterStyle.Builder -> "Dashboard control boards and operational flows"
                        PosterStyle.Catalog -> "Catalog views, storefront cards, and checkout rhythm"
                        PosterStyle.Motion -> "Media rails, highlight reels, and motion-first panels"
                    },
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
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = content
        )
    }
}

@Composable
private fun BuilderPosterLayout(palette: PosterPalette) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            PosterMetricCard(
                modifier = Modifier.weight(1f),
                title = "Tasks",
                value = "48",
                palette = palette
            )
            PosterMetricCard(
                modifier = Modifier.weight(1f),
                title = "Queues",
                value = "12",
                palette = palette
            )
        }
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
                listOf(0.38f, 0.74f, 0.56f, 0.88f, 0.46f).forEach { scale ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(scale)
                            .background(
                                if (scale > 0.8f) palette.accent else palette.accentSoft,
                                RoundedCornerShape(999.dp)
                            )
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .background(
                            if (index == 1) palette.accent.copy(alpha = 0.85f) else palette.card,
                            RoundedCornerShape(14.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun CatalogPosterLayout(palette: PosterPalette) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            repeat(2) { index ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(palette.card, RoundedCornerShape(16.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .background(
                                if (index == 0) palette.accentSoft else palette.accent.copy(alpha = 0.85f),
                                RoundedCornerShape(12.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(8.dp)
                            .background(palette.text.copy(alpha = 0.24f), RoundedCornerShape(999.dp))
                    )
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(8.dp)
                            .background(palette.text.copy(alpha = 0.14f), RoundedCornerShape(999.dp))
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("New", "Top", "Fast").forEach { label ->
                PosterPill(label, palette.card, palette.text.copy(alpha = 0.8f))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(palette.card, RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .background(
                                if (index == 2) palette.accent.copy(alpha = 0.85f) else palette.accentSoft,
                                RoundedCornerShape(12.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun MotionPosterLayout(palette: PosterPalette) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(0.42f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp + (index * 3).dp)
                            .background(
                                if (index == 0) palette.accent.copy(alpha = 0.85f) else palette.card,
                                RoundedCornerShape(12.dp)
                            )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.58f)
                    .height(118.dp)
                    .background(palette.card, RoundedCornerShape(18.dp))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(44.dp)
                        .background(palette.accent, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 4.dp)
                        .size(width = 12.dp, height = 14.dp)
                        .background(palette.text, RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf(44.dp, 64.dp, 54.dp, 74.dp, 40.dp).forEachIndexed { index, height ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(height)
                        .background(
                            if (index % 2 == 0) palette.accentSoft else palette.accent.copy(alpha = 0.85f),
                            RoundedCornerShape(999.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun PosterMetricCard(
    modifier: Modifier,
    title: String,
    value: String,
    palette: PosterPalette
) {
    Column(
        modifier = modifier
            .background(palette.card, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = palette.text.copy(alpha = 0.68f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = palette.text
        )
    }
}

private fun posterPalette(style: PosterStyle): PosterPalette {
    return when (style) {
        PosterStyle.Builder -> PosterPalette(
            backgroundTop = Color(0xFF111111),
            backgroundBottom = Color(0xFF2A1114),
            accent = Color(0xFFFF4D5A),
            accentSoft = Color(0xFF5A2228),
            card = Color(0xFF1D1D1D),
            text = Color(0xFFF8F8F8)
        )
        PosterStyle.Catalog -> PosterPalette(
            backgroundTop = Color(0xFF171717),
            backgroundBottom = Color(0xFF2F2F2F),
            accent = Color(0xFFFF5964),
            accentSoft = Color(0xFFE2E2E2),
            card = Color(0xFF232323),
            text = Color(0xFFF5F5F5)
        )
        PosterStyle.Motion -> PosterPalette(
            backgroundTop = Color(0xFF0F0F10),
            backgroundBottom = Color(0xFF241114),
            accent = Color(0xFFFF5B4D),
            accentSoft = Color(0xFF707070),
            card = Color(0xFF1A1A1A),
            text = Color(0xFFFFFFFF)
        )
    }
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
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.78f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, androidx.compose.foundation.shape.RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(action.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Text(action.title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text(action.summary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Text(action.buttonLabel)
            }
        }
    }
}

@Composable
internal fun ActionRow(
    left: ActionItem,
    right: ActionItem,
    stacked: Boolean,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {
    ResponsivePair(
        stacked = stacked,
        first = { modifier -> ActionCard(left, modifier, onLeftClick) },
        second = { modifier -> ActionCard(right, modifier, onRightClick) }
    )
}

@Composable
internal fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
internal fun PortalWebView(url: String, height: Dp) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
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
                if (webView.url != url) {
                    webView.loadUrl(url)
                }
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

    runCatching {
        context.startActivity(intent)
    }.recoverCatching {
        context.startActivity(Intent.createChooser(intent, "Open with"))
    }.getOrElse {
        if (it !is ActivityNotFoundException) {
            throw it
        }
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
    }.getOrElse {
        false
    }
}
