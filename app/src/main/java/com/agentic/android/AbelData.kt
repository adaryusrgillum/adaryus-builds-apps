package com.agentic.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

internal const val StudioEmail = "hello@adaryusbuilds.app"
internal const val StudioAvailability = "Samsung-ready APK layouts for phones, foldables, and tablets."
internal const val StudioStyleNote = "Neon-cyber styling with cyan, purple, green glow, dark glass, and motion."
internal const val StudioDeliveryNote = "Built with Jetpack Compose for fast, modern Android presentation."

internal enum class BuildRequestType(
    val label: String,
    val emailSubject: String,
    val hint: String
) {
    BusinessSuite(
        label = "Business Suite",
        emailSubject = "Business Suite Build Request",
        hint = "Great for dashboards, approvals, staff tools, and analytics-focused admin apps."
    ),
    Storefront(
        label = "Storefront",
        emailSubject = "Storefront Build Request",
        hint = "Ideal for product catalogs, food ordering, booking flows, and customer purchases."
    ),
    MediaHub(
        label = "Media Hub",
        emailSubject = "Media Hub Build Request",
        hint = "Use this when the app needs reels, video collections, podcasts, or streaming panels."
    ),
    UtilityTool(
        label = "Utility Tool",
        emailSubject = "Utility Tool Build Request",
        hint = "A strong fit for scanners, converters, maintenance tools, and offline helper apps."
    ),
    LearningApp(
        label = "Learning App",
        emailSubject = "Learning App Build Request",
        hint = "Choose this for lessons, quizzes, certifications, coaching, or onboarding apps."
    ),
    SecurePlatform(
        label = "Secure Platform",
        emailSubject = "Secure Platform Build Request",
        hint = "Best for access control, alerts, identity checks, admin roles, and device security."
    )
}

internal data class AppCategory(
    val title: String,
    val summary: String,
    val features: List<String>,
    val icon: ImageVector,
    val imageRes: Int,
    val accentLabel: String,
    val requestType: BuildRequestType
)

internal data class ActionItem(
    val title: String,
    val summary: String,
    val icon: ImageVector,
    val buttonLabel: String = "Open"
)

internal data class ShowcaseCard(
    val title: String,
    val summary: String,
    val tags: List<String>,
    val imageRes: Int
)

internal data class StudioHighlight(
    val headline: String,
    val detail: String
)

internal data class BuildPhase(
    val title: String,
    val summary: String,
    val icon: ImageVector
)

internal val appCategories = listOf(
    AppCategory(
        title = "Business Systems",
        summary = "Operational apps for teams that need dashboards, approvals, reports, and daily workflows in one clean mobile experience.",
        features = listOf("Dashboards", "Role-based views", "Reports", "Task queues"),
        icon = Icons.Outlined.Business,
        imageRes = R.drawable.adaryus_poster_builder,
        accentLabel = "Admin-first",
        requestType = BuildRequestType.BusinessSuite
    ),
    AppCategory(
        title = "Storefront And Ordering",
        summary = "Customer-facing apps that make browsing, booking, product discovery, and checkout feel fast and premium.",
        features = listOf("Catalogs", "Cart flow", "Offers", "Order tracking"),
        icon = Icons.Outlined.ShoppingCart,
        imageRes = R.drawable.adaryus_poster_catalog,
        accentLabel = "Conversion-ready",
        requestType = BuildRequestType.Storefront
    ),
    AppCategory(
        title = "Media Hubs",
        summary = "Visual experiences for reels, video feeds, music, podcasts, or highlight-driven storytelling.",
        features = listOf("Reels", "Collections", "Highlights", "Trending rows"),
        icon = Icons.Outlined.Movie,
        imageRes = R.drawable.adaryus_poster_motion,
        accentLabel = "Motion-heavy",
        requestType = BuildRequestType.MediaHub
    ),
    AppCategory(
        title = "Utility Tools",
        summary = "Focused apps for scanning, converting, tracking, or simplifying one task exceptionally well.",
        features = listOf("Scanners", "Offline tools", "Shortcuts", "Quick actions"),
        icon = Icons.Outlined.Build,
        imageRes = R.drawable.adaryus_poster_builder,
        accentLabel = "Fast utility",
        requestType = BuildRequestType.UtilityTool
    ),
    AppCategory(
        title = "Learning Platforms",
        summary = "Structured learning flows that present lessons, progress, certificates, and guided practice cleanly on mobile.",
        features = listOf("Lessons", "Quizzes", "Progress", "Certificates"),
        icon = Icons.Outlined.School,
        imageRes = R.drawable.adaryus_poster_catalog,
        accentLabel = "Growth-driven",
        requestType = BuildRequestType.LearningApp
    ),
    AppCategory(
        title = "Security Platforms",
        summary = "High-clarity apps for alerts, verification, monitoring, secure access, and device-aware workflows.",
        features = listOf("Alerts", "Identity", "Monitoring", "Control panels"),
        icon = Icons.Outlined.Security,
        imageRes = R.drawable.adaryus_poster_motion,
        accentLabel = "Protected UI",
        requestType = BuildRequestType.SecurePlatform
    )
)

internal val homeQuickActions = listOf(
    ActionItem("View Categories", "Browse multiple application categories and their key modules.", Icons.Outlined.Dashboard, "Browse"),
    ActionItem("Open Gallery", "See image-driven screens and motion cues inspired by premium Android builds.", Icons.Outlined.Palette, "View"),
    ActionItem("Start A Brief", "Open the request form and shape the kind of APK you want built.", Icons.Outlined.Email, "Start"),
    ActionItem("Samsung Ready", "Review the design notes for high-contrast layouts built for Android devices.", Icons.Outlined.PhoneAndroid, "Read")
)

internal val showcaseCards = listOf(
    ShowcaseCard(
        title = "Command Center",
        summary = "A control-heavy layout with live stats, bold framing, and fast-action panels for operational work.",
        tags = listOf("Analytics", "Admin", "Approvals"),
        imageRes = R.drawable.adaryus_poster_builder
    ),
    ShowcaseCard(
        title = "Visual Catalog",
        summary = "A clean, high-contrast store view that keeps products, cards, and filters easy to scan on Samsung screens.",
        tags = listOf("Products", "Offers", "Checkout"),
        imageRes = R.drawable.adaryus_poster_catalog
    ),
    ShowcaseCard(
        title = "Motion Feed",
        summary = "A dramatic media surface with highlight blocks, animated rhythm, and strong visual hierarchy.",
        tags = listOf("Reels", "Media", "Discovery"),
        imageRes = R.drawable.adaryus_poster_motion
    )
)

internal val studioHighlights = listOf(
    StudioHighlight("Bold Contrast", "Deep black panels, sharp white content, and controlled red accents keep the UI high-impact."),
    StudioHighlight("Premium Motion", "Animated glows, visibility transitions, and moving signal bars add life without clutter."),
    StudioHighlight("Category Depth", "Multiple app lanes are presented with clear summaries, image tiles, and request-ready actions."),
    StudioHighlight("Samsung Fit", "Layouts are responsive and comfortable for modern Android phones, including larger Samsung displays.")
)

internal val buildPhases = listOf(
    BuildPhase("Define The App Lane", "Choose the product type, modules, and audience so the experience has a clear point of view.", Icons.Outlined.Apps),
    BuildPhase("Shape The Visual System", "Lock the black, red, white, and gray styling with imagery and hierarchy that feels intentional.", Icons.Outlined.Palette),
    BuildPhase("Polish The Motion", "Add animation, navigation rhythm, and finishing detail so the app feels like a shipped product.", Icons.Outlined.FlashOn)
)
