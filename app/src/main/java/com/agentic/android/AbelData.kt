package com.agentic.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Water
import androidx.compose.ui.graphics.vector.ImageVector

internal const val StudioEmail = "hello@adaryusbuilds.app"
internal const val StudioAvailability =
    "Sales-focused Android, web, and cross-platform builds for phones, foldables, tablets, and launch campaigns."
internal const val StudioStyleNote =
    "Professional editorial direction with premium hierarchy, refined materials, calm contrast, and conversion-ready structure."
internal const val StudioDeliveryNote =
    "Built to help brands capture leads, close bookings, increase repeat business, and launch with a stronger digital sales system."

internal enum class BuildRequestType(
    val label: String,
    val emailSubject: String,
    val hint: String
) {
    SocialCommunity(
        label = "Social / Community",
        emailSubject = "Social Community App Request",
        hint = "Best when the business wins through membership, audience loyalty, recurring engagement, and customer retention."
    ),
    ProductivityBusiness(
        label = "Productivity / Business",
        emailSubject = "Productivity Business App Request",
        hint = "Ideal for businesses that need more leads converted, sales teams supported, and operations tightened around revenue."
    ),
    EntertainmentStreaming(
        label = "Entertainment / Streaming",
        emailSubject = "Entertainment Streaming App Request",
        hint = "Use this when the product needs attention, viewing time, content discovery, and stronger audience monetization."
    ),
    FinanceBanking(
        label = "Finance / Banking",
        emailSubject = "Finance Banking App Request",
        hint = "Strong for trust-heavy products where clearer account visibility, safer flows, and stronger confidence drive action."
    ),
    EducationLearning(
        label = "Education / Learning",
        emailSubject = "Education Learning App Request",
        hint = "Choose this when the business sells transformation through lessons, onboarding, certifications, or guided progress."
    ),
    UtilitiesNavigation(
        label = "Utilities / Navigation",
        emailSubject = "Utilities Navigation App Request",
        hint = "Great when the app should reduce friction, save time, and make everyday action easier enough to win repeat use."
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

internal data class SalesOutcome(
    val title: String,
    val detail: String,
    val icon: ImageVector
)

internal data class TrustSignal(
    val title: String,
    val detail: String
)

internal data class OfferPackage(
    val title: String,
    val fit: String,
    val deliverables: List<String>,
    val cta: String
)

internal data class ObjectionAnswer(
    val question: String,
    val answer: String
)

internal data class BudgetBand(
    val label: String,
    val description: String
)

internal data class TimelineBand(
    val label: String,
    val description: String
)

internal data class SalesProblem(
    val title: String,
    val detail: String,
    val icon: ImageVector
)

internal data class AiUpdate(
    val title: String,
    val summary: String,
    val sourceLabel: String,
    val icon: ImageVector
)

internal val appCategories = listOf(
    AppCategory(
        title = "Social / Community",
        summary = "Audience-led experiences that build loyalty, repeat attention, and stronger post-sale customer relationships.",
        features = listOf("Profiles", "Messaging", "Feeds", "Events"),
        icon = Icons.Outlined.Groups,
        posterStyle = PosterStyle.Community,
        accentLabel = "Retention-first",
        requestType = BuildRequestType.SocialCommunity
    ),
    AppCategory(
        title = "Productivity / Business",
        summary = "Operational systems that help teams close faster, track performance, and reduce manual sales friction.",
        features = listOf("Dashboards", "Approvals", "Tasks", "Reports"),
        icon = Icons.Outlined.Dashboard,
        posterStyle = PosterStyle.Productivity,
        accentLabel = "Revenue-ready",
        requestType = BuildRequestType.ProductivityBusiness
    ),
    AppCategory(
        title = "Entertainment / Streaming",
        summary = "Media-heavy apps built to win attention, increase watch time, and convert audience energy into revenue.",
        features = listOf("Reels", "Streaming", "Playlists", "Discovery"),
        icon = Icons.Outlined.Movie,
        posterStyle = PosterStyle.Entertainment,
        accentLabel = "Attention-rich",
        requestType = BuildRequestType.EntertainmentStreaming
    ),
    AppCategory(
        title = "Finance / Banking",
        summary = "High-trust interfaces that reduce doubt, strengthen confidence, and support high-value customer actions.",
        features = listOf("Accounts", "Transfers", "Tracking", "Security"),
        icon = Icons.Outlined.AccountBalance,
        posterStyle = PosterStyle.Finance,
        accentLabel = "Trust-driven",
        requestType = BuildRequestType.FinanceBanking
    ),
    AppCategory(
        title = "Education / Learning",
        summary = "Structured learning products that sell progress, transformation, and continued engagement through guided milestones.",
        features = listOf("Lessons", "Progress", "Quizzes", "Certificates"),
        icon = Icons.Outlined.School,
        posterStyle = PosterStyle.Education,
        accentLabel = "Transformation-led",
        requestType = BuildRequestType.EducationLearning
    ),
    AppCategory(
        title = "Utilities / Navigation",
        summary = "Useful tools that save time, reduce friction, and become the app customers return to because it makes life easier.",
        features = listOf("Maps", "Scanners", "Shortcuts", "Tracking"),
        icon = Icons.Outlined.Map,
        posterStyle = PosterStyle.Utility,
        accentLabel = "Low-friction utility",
        requestType = BuildRequestType.UtilitiesNavigation
    )
)

internal val appTechnologyTypes = listOf(
    AppTechnologyType(
        title = "Native Apps",
        summary = "Platform-specific apps built directly for Android or iPhone, with the best performance, polish, and device trust signals.",
        strengths = listOf("Best performance", "Full device access", "Highest polish"),
        tradeoffs = listOf("Separate platform builds", "Higher development effort"),
        bestFor = listOf("Sales tools", "Finance products", "High-trust flagship apps"),
        icon = Icons.Outlined.PhoneAndroid,
        posterStyle = PosterStyle.Productivity,
        accentLabel = "Performance-first",
        recommendedRequestType = BuildRequestType.ProductivityBusiness
    ),
    AppTechnologyType(
        title = "Web Apps",
        summary = "Responsive browser-based experiences that launch quickly, reduce friction, and work well for lead capture and reach.",
        strengths = listOf("Fast deployment", "Works in browser", "Broad access"),
        tradeoffs = listOf("Lower device integration", "Usually needs internet"),
        bestFor = listOf("Lead funnels", "Portals", "Content products"),
        icon = Icons.Outlined.Language,
        posterStyle = PosterStyle.Community,
        accentLabel = "Reach-first",
        recommendedRequestType = BuildRequestType.SocialCommunity
    ),
    AppTechnologyType(
        title = "Hybrid Apps",
        summary = "Installed apps powered by web technologies, useful when speed to market matters more than peak native performance.",
        strengths = listOf("Faster multi-platform delivery", "Shared UI logic", "App-store presence"),
        tradeoffs = listOf("Some performance compromise", "More abstraction"),
        bestFor = listOf("Simple commerce", "Internal tools", "Utility products"),
        icon = Icons.Outlined.Layers,
        posterStyle = PosterStyle.Utility,
        accentLabel = "Balanced build",
        recommendedRequestType = BuildRequestType.UtilitiesNavigation
    ),
    AppTechnologyType(
        title = "Progressive Web Apps",
        summary = "Web apps with install-like behavior, ideal for businesses that want lightweight access and faster launch economics.",
        strengths = listOf("Fast to ship", "Install-like behavior", "Lower launch friction"),
        tradeoffs = listOf("Less app-store presence", "Limited native integrations"),
        bestFor = listOf("Ordering", "Field tools", "Service access"),
        icon = Icons.Outlined.Cloud,
        posterStyle = PosterStyle.Utility,
        accentLabel = "Lean delivery",
        recommendedRequestType = BuildRequestType.UtilitiesNavigation
    ),
    AppTechnologyType(
        title = "Cross-Platform Apps",
        summary = "Single-codebase products designed for Android and iPhone when consistency, speed, and efficient growth all matter.",
        strengths = listOf("Shared codebase", "Consistent UI", "Good cost efficiency"),
        tradeoffs = listOf("Not always fully native", "Platform tuning still needed"),
        bestFor = listOf("Education products", "Media brands", "Growth-stage launches"),
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
    "Beautiful products lower doubt before the sales call starts.",
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

internal val conversionFocusPhrases = listOf(
    "Most businesses do not have a traffic problem. They have a conversion problem.",
    "If buyers do not trust the first screen, they never reach the offer.",
    "A premium product experience lowers doubt before the sales call starts.",
    "Clear positioning turns interest into qualified inbound leads.",
    "The cost of confusion is missed revenue.",
    "Your mobile experience should close, not just impress.",
    "If the next step is unclear, the sale is weaker.",
    "Better customer flow means fewer lost buyers."
)

internal val homeQuickActions = listOf(
    ActionItem("Explore Categories", "Browse the product types Adaryus can turn into lead capture, sales, retention, and operational growth tools.", Icons.Outlined.Apps, "Browse"),
    ActionItem("App Types Guide", "Compare build paths so your budget, timeline, and business goals stay aligned from day one.", Icons.Outlined.Code, "Learn"),
    ActionItem("Showcase Boards", "Review conversion-ready boards for offers, trust, mobile actions, and premium customer flow.", Icons.AutoMirrored.Outlined.TrendingUp, "View"),
    ActionItem("Start Build Brief", "Open the request flow and turn your idea, offer, and sales goal into a qualified project brief.", Icons.Outlined.Email, "Start")
)

internal val showcaseCards = listOf(
    ShowcaseCard(
        title = "Signal Console",
        summary = "A business dashboard direction built to support teams, simplify action, and keep sales or ops decisions visible.",
        tags = listOf("Operations", "Workflow", "Dashboards"),
        posterStyle = PosterStyle.Productivity,
        requestType = BuildRequestType.ProductivityBusiness
    ),
    ShowcaseCard(
        title = "Stream House",
        summary = "A media-led concept designed to hold attention longer, improve discovery, and convert audience energy into revenue.",
        tags = listOf("Streaming", "Content", "Discovery"),
        posterStyle = PosterStyle.Entertainment,
        requestType = BuildRequestType.EntertainmentStreaming
    ),
    ShowcaseCard(
        title = "Vault Ledger",
        summary = "A finance board shaped to reduce doubt, reinforce trust, and support high-value customer actions cleanly.",
        tags = listOf("Finance", "Security", "Analytics"),
        posterStyle = PosterStyle.Finance,
        requestType = BuildRequestType.FinanceBanking
    ),
    ShowcaseCard(
        title = "Route Utility",
        summary = "A focused mobile utility layer designed to reduce friction, save time, and stay useful enough for repeat use.",
        tags = listOf("Navigation", "Utility", "Maps"),
        posterStyle = PosterStyle.Utility,
        requestType = BuildRequestType.UtilitiesNavigation
    )
)

internal val studioHighlights = listOf(
    StudioHighlight("Professional Positioning", "The experience now speaks to business outcomes, not just visuals, so the product feels more credible to buyers."),
    StudioHighlight("Sales Structure", "Screens are framed around lead capture, offers, retention, and clarity so prospects understand why the app matters."),
    StudioHighlight("Trust-First Design", "Light premium surfaces, stronger typography, and better hierarchy make the product feel more established and capable."),
    StudioHighlight("Premium Atmosphere", "Soft materials, lighter space, and calmer accents create a richer impression while keeping the sales message clear.")
)

internal val buildPhases = listOf(
    BuildPhase("Strategy", "Clarify the offer, audience, and business goal so the build supports real sales outcomes from the start.", Icons.AutoMirrored.Outlined.TrendingUp),
    BuildPhase("Design", "Shape the premium mobile flow, trust hierarchy, and buyer journey before development starts.", Icons.Outlined.Palette),
    BuildPhase("Build", "Turn the approved direction into Android, web, or cross-platform delivery with conversion clarity intact.", Icons.Outlined.Code),
    BuildPhase("Launch", "Refine the final CTA flow, polish the release, and ship with a stronger path to inquiry, booking, or purchase.", Icons.AutoMirrored.Outlined.TrendingUp)
)

internal val salesProblems = listOf(
    SalesProblem("Low Lead Volume", "Too many visitors leave without inquiring because the offer and next step are weak or unclear.", Icons.Outlined.Email),
    SalesProblem("Poor Conversion", "The business gets attention, but buyers hesitate because trust, proof, and CTA flow are not strong enough.", Icons.AutoMirrored.Outlined.TrendingUp),
    SalesProblem("Weak Repeat Business", "Customers buy once but the product experience does not keep them engaged enough to return.", Icons.Outlined.Groups),
    SalesProblem("Manual Sales Friction", "The team spends too much time answering basic questions, routing requests, or moving work that the product could handle.", Icons.Outlined.Dashboard)
)

internal val salesOutcomes = listOf(
    SalesOutcome("Capture More Leads", "Give visitors a clearer path to inquire, book, subscribe, or request a quote without friction.", Icons.Outlined.Email),
    SalesOutcome("Convert More Buyers", "Use stronger hierarchy, trust cues, and better mobile UX to turn attention into action.", Icons.AutoMirrored.Outlined.TrendingUp),
    SalesOutcome("Increase Repeat Business", "Support repeat orders, member retention, account access, and post-sale loyalty.", Icons.Outlined.Groups)
)

internal val trustSignals = listOf(
    TrustSignal("Outcome-Led Planning", "Every screen should support a business goal like bookings, purchases, retention, or qualified inbound leads."),
    TrustSignal("Launch-Ready Delivery", "The app, companion web presence, and creative direction are shaped together as one sales system."),
    TrustSignal("Professional Buyer Flow", "Navigation, CTAs, and request steps are tuned to feel more credible and easier to say yes to.")
)

internal val aiUpdates = listOf(
    AiUpdate(
        title = "Private local models are practical now",
        summary = "Ollama makes it straightforward to run and integrate local models through a stable local API, which is a strong fit for privacy-first demos, internal copilots, and offline-friendly workflows.",
        sourceLabel = "Verified with Ollama docs",
        icon = Icons.Outlined.Memory
    ),
    AiUpdate(
        title = "Tool-connected agents are becoming the real workflow layer",
        summary = "MCP and similar tool protocols matter because the model becomes more useful when it can safely reach the right docs, systems, and actions instead of staying trapped in chat.",
        sourceLabel = "Verified with MCP + Anthropic docs",
        icon = Icons.Outlined.Hub
    ),
    AiUpdate(
        title = "Edge AI is moving from cloud-only to on-device",
        summary = "NVIDIA’s Jetson line keeps pushing private, low-latency AI closer to the device, which opens the door for smarter assistants, robotics, and always-on local inference.",
        sourceLabel = "Verified with NVIDIA Jetson sources",
        icon = Icons.Outlined.Devices
    ),
    AiUpdate(
        title = "Realtime voice is now part of the product stack",
        summary = "Voice interfaces are getting more practical thanks to realtime speech and translation tooling, which means assistants can feel more natural and more branded than before.",
        sourceLabel = "Verified with NVIDIA Riva sources",
        icon = Icons.Outlined.GraphicEq
    ),
    AiUpdate(
        title = "Security has to be designed in from the start",
        summary = "As agents gain access to tools and data, zero-trust thinking matters more. The safer product is the one that assumes access should be explicit, scoped, and monitored.",
        sourceLabel = "Verified with IBM zero-trust guidance",
        icon = Icons.Outlined.Security
    )
)

internal val offerPackages = listOf(
    OfferPackage(
        title = "Starter",
        fit = "Best for small businesses that need a sharper offer, stronger lead capture, and a clean first mobile presence.",
        deliverables = listOf("Sales-focused app direction", "Core lead capture flow", "One platform launch plan"),
        cta = "Start with Starter"
    ),
    OfferPackage(
        title = "Growth",
        fit = "Best for businesses ready to improve conversion, automate parts of the customer flow, and launch a stronger sales system.",
        deliverables = listOf("Premium buyer journey", "Android + web alignment", "Conversion-focused content structure"),
        cta = "Choose Growth"
    ),
    OfferPackage(
        title = "Premium",
        fit = "Best for brands that need a flagship product, stronger trust on first look, and a launch built to support serious revenue goals.",
        deliverables = listOf("Full sales-system planning", "Advanced app concept boards", "High-touch launch positioning"),
        cta = "Plan Premium"
    )
)

internal val objectionAnswers = listOf(
    ObjectionAnswer(
        question = "Will this just look good, or will it help the business sell?",
        answer = "The direction is built around business goals first: stronger offer clarity, better lead capture, cleaner trust signals, and a clearer next step."
    ),
    ObjectionAnswer(
        question = "How do I know which platform path makes sense?",
        answer = "The app types guide and build brief are designed to match your budget, timeline, audience, and sales goal before development starts."
    ),
    ObjectionAnswer(
        question = "What if I am not ready for a huge build yet?",
        answer = "That is why the engagement models are split into Starter, Growth, and Premium so the business can move at the right level without overcommitting."
    ),
    ObjectionAnswer(
        question = "How fast can we move from idea to something credible?",
        answer = "The funnel is structured to qualify the project quickly, reduce ambiguity, and make the next sales conversation about direction instead of guesswork."
    )
)

internal val budgetBands = listOf(
    BudgetBand("Exploring", "Still validating the opportunity and need a realistic recommendation first."),
    BudgetBand("Starter Budget", "Need a focused first release that improves trust and lead capture without a huge initial scope."),
    BudgetBand("Growth Budget", "Ready for a stronger product and a more complete sales journey across app and web."),
    BudgetBand("Premium Budget", "Prepared to invest in a flagship experience that supports a bigger launch or revenue goal.")
)

internal val timelineBands = listOf(
    TimelineBand("ASAP", "Need momentum quickly because the business has an immediate sales or launch priority."),
    TimelineBand("30-45 Days", "Ready to move soon with a defined offer and faster delivery expectations."),
    TimelineBand("60-90 Days", "Want to plan carefully and launch with a more complete product direction."),
    TimelineBand("Strategy First", "Need to clarify the offer, audience, and scope before setting a build date.")
)
