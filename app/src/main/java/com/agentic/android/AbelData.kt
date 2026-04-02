package com.agentic.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Water
import androidx.compose.ui.graphics.vector.ImageVector

internal const val StudioEmail = "hello@adaryusbuilds.app"
internal const val StudioAvailability = "Samsung-ready APK layouts for phones, foldables, and tablets."
internal const val StudioStyleNote = "Fire-lit black, ember red, flame orange, molten yellow, subtle koi motion, and manifesto-wall atmosphere."
internal const val StudioDeliveryNote = "Built with Jetpack Compose and a matching web companion for a premium Adaryus showcase."

internal enum class BuildRequestType(
    val label: String,
    val emailSubject: String,
    val hint: String
) {
    SocialCommunity(
        label = "Social / Community",
        emailSubject = "Social Community App Request",
        hint = "Best for member feeds, groups, creator spaces, events, and audience-led engagement products."
    ),
    ProductivityBusiness(
        label = "Productivity / Business",
        emailSubject = "Productivity Business App Request",
        hint = "Ideal for dashboards, approvals, workflows, sales tools, operations, and internal team systems."
    ),
    EntertainmentStreaming(
        label = "Entertainment / Streaming",
        emailSubject = "Entertainment Streaming App Request",
        hint = "Use this for video, music, short-form content, streaming rails, and media-first discovery."
    ),
    FinanceBanking(
        label = "Finance / Banking",
        emailSubject = "Finance Banking App Request",
        hint = "A strong fit for wallets, account views, tracking, payments, analytics, and security-heavy money flows."
    ),
    EducationLearning(
        label = "Education / Learning",
        emailSubject = "Education Learning App Request",
        hint = "Choose this for lessons, certifications, progress systems, onboarding, and guided learning products."
    ),
    UtilitiesNavigation(
        label = "Utilities / Navigation",
        emailSubject = "Utilities Navigation App Request",
        hint = "Great for tools, maps, scanners, scheduling helpers, quick-action apps, and everyday mobile utility."
    )
}

internal enum class PosterStyle {
    Community,
    Productivity,
    Entertainment,
    Finance,
    Education,
    Utility
}

internal data class AppCategory(
    val title: String,
    val summary: String,
    val features: List<String>,
    val icon: ImageVector,
    val posterStyle: PosterStyle,
    val accentLabel: String,
    val requestType: BuildRequestType
)

internal data class AppTechnologyType(
    val title: String,
    val summary: String,
    val strengths: List<String>,
    val tradeoffs: List<String>,
    val bestFor: List<String>,
    val icon: ImageVector,
    val posterStyle: PosterStyle,
    val accentLabel: String,
    val recommendedRequestType: BuildRequestType
)

internal data class KoiDepthBand(
    val label: String,
    val sizeScale: Float,
    val alpha: Float,
    val speedMultiplier: Float
)

internal data class KoiSceneSpec(
    val fishCount: Int,
    val speedRange: IntRange,
    val depthBands: List<KoiDepthBand>,
    val rippleIntensity: Float,
    val phraseWallOpacity: Float
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
    val posterStyle: PosterStyle,
    val requestType: BuildRequestType
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
        title = "Social / Community",
        summary = "Audience-led experiences for member feeds, groups, live interaction, and community momentum.",
        features = listOf("Profiles", "Messaging", "Feeds", "Events"),
        icon = Icons.Outlined.Groups,
        posterStyle = PosterStyle.Community,
        accentLabel = "Connection-first",
        requestType = BuildRequestType.SocialCommunity
    ),
    AppCategory(
        title = "Productivity / Business",
        summary = "Operational systems for teams that need dashboards, approvals, tasks, and revenue-driving workflow clarity.",
        features = listOf("Dashboards", "Approvals", "Tasks", "Reports"),
        icon = Icons.Outlined.Dashboard,
        posterStyle = PosterStyle.Productivity,
        accentLabel = "Execution-ready",
        requestType = BuildRequestType.ProductivityBusiness
    ),
    AppCategory(
        title = "Entertainment / Streaming",
        summary = "Media-heavy apps built for reels, playback, discovery, and content browsing with premium motion.",
        features = listOf("Reels", "Streaming", "Playlists", "Discovery"),
        icon = Icons.Outlined.Movie,
        posterStyle = PosterStyle.Entertainment,
        accentLabel = "Motion-rich",
        requestType = BuildRequestType.EntertainmentStreaming
    ),
    AppCategory(
        title = "Finance / Banking",
        summary = "High-trust interfaces for balances, transfers, analytics, account overviews, and controlled secure flows.",
        features = listOf("Accounts", "Transfers", "Tracking", "Security"),
        icon = Icons.Outlined.AccountBalance,
        posterStyle = PosterStyle.Finance,
        accentLabel = "Trust-driven",
        requestType = BuildRequestType.FinanceBanking
    ),
    AppCategory(
        title = "Education / Learning",
        summary = "Structured learning products that make lessons, milestones, feedback, and mastery clear on mobile.",
        features = listOf("Lessons", "Progress", "Quizzes", "Certificates"),
        icon = Icons.Outlined.School,
        posterStyle = PosterStyle.Education,
        accentLabel = "Progress-led",
        requestType = BuildRequestType.EducationLearning
    ),
    AppCategory(
        title = "Utilities / Navigation",
        summary = "Focused tools for scanners, maps, quick actions, trip flows, and everyday useful mobile behavior.",
        features = listOf("Maps", "Scanners", "Shortcuts", "Tracking"),
        icon = Icons.Outlined.Map,
        posterStyle = PosterStyle.Utility,
        accentLabel = "Everyday utility",
        requestType = BuildRequestType.UtilitiesNavigation
    )
)

internal val appTechnologyTypes = listOf(
    AppTechnologyType(
        title = "Native Apps",
        summary = "Platform-specific apps built directly for Android or iPhone, with the strongest device performance and deepest hardware access.",
        strengths = listOf("Best performance", "Full device access", "High security"),
        tradeoffs = listOf("Separate platform builds", "Higher development effort"),
        bestFor = listOf("Finance tools", "Productivity systems", "High-polish flagship apps"),
        icon = Icons.Outlined.PhoneAndroid,
        posterStyle = PosterStyle.Productivity,
        accentLabel = "Performance-first",
        recommendedRequestType = BuildRequestType.ProductivityBusiness
    ),
    AppTechnologyType(
        title = "Web Apps",
        summary = "Responsive browser-based experiences that are easier to launch broadly and work well for reach, content, and portal-style products.",
        strengths = listOf("Fast deployment", "Works in browser", "Single web codebase"),
        tradeoffs = listOf("Lower device integration", "Usually needs internet"),
        bestFor = listOf("Portals", "Content platforms", "Community hubs"),
        icon = Icons.Outlined.Language,
        posterStyle = PosterStyle.Community,
        accentLabel = "Reach-first",
        recommendedRequestType = BuildRequestType.SocialCommunity
    ),
    AppTechnologyType(
        title = "Hybrid Apps",
        summary = "Installed mobile apps powered by web technologies, offering faster cross-platform delivery while retaining app-store presence.",
        strengths = listOf("Faster multi-platform delivery", "Shared UI logic", "App-store install flow"),
        tradeoffs = listOf("Some performance compromise", "More abstraction"),
        bestFor = listOf("Business tools", "Simple commerce", "Utilities"),
        icon = Icons.Outlined.Layers,
        posterStyle = PosterStyle.Utility,
        accentLabel = "Balanced build",
        recommendedRequestType = BuildRequestType.UtilitiesNavigation
    ),
    AppTechnologyType(
        title = "Progressive Web Apps",
        summary = "Web applications that behave more like installed apps, adding offline support, quick launch, and lightweight install behavior.",
        strengths = listOf("Fast to ship", "Install-like behavior", "Good for lightweight mobile access"),
        tradeoffs = listOf("Less app-store presence", "Limited native integrations"),
        bestFor = listOf("Ordering", "Field tools", "Content access"),
        icon = Icons.Outlined.Cloud,
        posterStyle = PosterStyle.Utility,
        accentLabel = "Lean delivery",
        recommendedRequestType = BuildRequestType.UtilitiesNavigation
    ),
    AppTechnologyType(
        title = "Cross-Platform Apps",
        summary = "Single-codebase products designed to run on both Android and iPhone while balancing speed, consistency, and native-like polish.",
        strengths = listOf("Shared codebase", "Consistent UI", "Good cost efficiency"),
        tradeoffs = listOf("Not always fully native", "Platform-specific tuning still needed"),
        bestFor = listOf("Education apps", "Entertainment products", "Brand-led mobile launches"),
        icon = Icons.Outlined.Devices,
        posterStyle = PosterStyle.Entertainment,
        accentLabel = "Scale-efficient",
        recommendedRequestType = BuildRequestType.EntertainmentStreaming
    )
)

internal val ambientKoiSpec = KoiSceneSpec(
    fishCount = 0,
    speedRange = 22000..30000,
    depthBands = listOf(
        KoiDepthBand("far", sizeScale = 0.72f, alpha = 0.14f, speedMultiplier = 1.15f)
    ),
    rippleIntensity = 0.08f,
    phraseWallOpacity = 0.09f
)

internal val heroKoiSpec = KoiSceneSpec(
    fishCount = 6,
    speedRange = 18000..26000,
    depthBands = listOf(
        KoiDepthBand("far", sizeScale = 0.72f, alpha = 0.24f, speedMultiplier = 1.18f),
        KoiDepthBand("mid", sizeScale = 0.94f, alpha = 0.36f, speedMultiplier = 1.0f),
        KoiDepthBand("near", sizeScale = 1.18f, alpha = 0.48f, speedMultiplier = 0.86f)
    ),
    rippleIntensity = 0.20f,
    phraseWallOpacity = 0.12f
)

internal val guideKoiSpec = KoiSceneSpec(
    fishCount = 5,
    speedRange = 19000..28000,
    depthBands = listOf(
        KoiDepthBand("far", sizeScale = 0.68f, alpha = 0.20f, speedMultiplier = 1.1f),
        KoiDepthBand("mid", sizeScale = 0.88f, alpha = 0.32f, speedMultiplier = 0.98f),
        KoiDepthBand("near", sizeScale = 1.06f, alpha = 0.40f, speedMultiplier = 0.90f)
    ),
    rippleIntensity = 0.16f,
    phraseWallOpacity = 0.10f
)

internal val showcaseKoiSpec = KoiSceneSpec(
    fishCount = 4,
    speedRange = 20000..30000,
    depthBands = listOf(
        KoiDepthBand("far", sizeScale = 0.74f, alpha = 0.18f, speedMultiplier = 1.14f),
        KoiDepthBand("mid", sizeScale = 0.96f, alpha = 0.30f, speedMultiplier = 0.98f),
        KoiDepthBand("near", sizeScale = 1.10f, alpha = 0.42f, speedMultiplier = 0.88f)
    ),
    rippleIntensity = 0.18f,
    phraseWallOpacity = 0.10f
)

internal val fireManifestoPhrases = listOf(
    "Your brand is your promise to the world.",
    "AI does not replace strategy. It amplifies it.",
    "Every pixel tells a story.",
    "Scale is not a dream. It is a system.",
    "The best marketing does not feel like marketing.",
    "Your app is your empire in your pocket.",
    "Design with intention. Build with precision.",
    "The right message at the right time changes everything.",
    "Behind every great brand is a system that scales.",
    "Adaryus builds what businesses need to win.",
    "Advertising is the spark. Your product is the fire.",
    "Build apps that solve real problems for real people.",
    "Code the future. Market the vision. Scale the mission.",
    "Innovation favors the prepared.",
    "Turn browsers into buyers.",
    "Organic growth is compound interest for your brand.",
    "Let your technology do the heavy lifting.",
    "From startup to standout.",
    "Growth starts with the right architecture.",
    "Your brand identity is your unfair advantage."
)

internal val homeQuickActions = listOf(
    ActionItem("Explore Categories", "Browse the functional app categories Adaryus can shape into premium products.", Icons.Outlined.Apps, "Browse"),
    ActionItem("App Types Guide", "Understand native, web, hybrid, PWA, and cross-platform build directions.", Icons.Outlined.Code, "Learn"),
    ActionItem("Showcase Boards", "Open cinematic fire-and-koi boards for commerce, media, finance, and utility.", Icons.Outlined.LocalFireDepartment, "View"),
    ActionItem("Start Build Brief", "Open the request flow and turn the idea into a studio-ready brief.", Icons.Outlined.Email, "Start")
)

internal val showcaseCards = listOf(
    ShowcaseCard(
        title = "Signal Console",
        summary = "A productivity command layer with strong metrics, guided workflow, and calm executive visibility.",
        tags = listOf("Operations", "Workflow", "Dashboards"),
        posterStyle = PosterStyle.Productivity,
        requestType = BuildRequestType.ProductivityBusiness
    ),
    ShowcaseCard(
        title = "Stream House",
        summary = "A cinematic content surface built for media discovery, motion hierarchy, and premium browsing rhythm.",
        tags = listOf("Streaming", "Content", "Discovery"),
        posterStyle = PosterStyle.Entertainment,
        requestType = BuildRequestType.EntertainmentStreaming
    ),
    ShowcaseCard(
        title = "Vault Ledger",
        summary = "A secure finance board blending account trust, analytics clarity, and high-contrast mobile confidence.",
        tags = listOf("Finance", "Security", "Analytics"),
        posterStyle = PosterStyle.Finance,
        requestType = BuildRequestType.FinanceBanking
    ),
    ShowcaseCard(
        title = "Route Utility",
        summary = "A compact, useful mobile tool layer for navigation, field work, quick actions, and map-aware tasks.",
        tags = listOf("Navigation", "Utility", "Maps"),
        posterStyle = PosterStyle.Utility,
        requestType = BuildRequestType.UtilitiesNavigation
    )
)

internal val studioHighlights = listOf(
    StudioHighlight("Fire Palette", "Near-black surfaces, ember red glows, flame orange light, and molten yellow accents build a richer signature look."),
    StudioHighlight("Koi Motion", "Subtle original koi animation adds realism and depth behind the hero, guide, and showcase layers."),
    StudioHighlight("Manifesto Wall", "The original phrase-wall idea stays alive as low-opacity atmosphere behind the interface."),
    StudioHighlight("Studio Focus", "The product still reads clearly as an Adaryus showcase first, with stronger education around app categories and build types.")
)

internal val buildPhases = listOf(
    BuildPhase("Spark The Direction", "Choose the category, the platform approach, and the audience so the product starts with a sharp point of view.", Icons.Outlined.LocalFireDepartment),
    BuildPhase("Shape The Pond", "Layer fire-lit interface, koi atmosphere, and premium hierarchy into one coherent visual system.", Icons.Outlined.Water),
    BuildPhase("Launch With Clarity", "Refine the motion, polish the build brief, and make the app feel like something already ready to ship.", Icons.Outlined.FlashOn)
)
