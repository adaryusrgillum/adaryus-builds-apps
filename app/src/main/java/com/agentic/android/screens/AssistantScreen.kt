package com.agentic.android.screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.outlined.VolumeOff
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.agentic.android.BuildRequestType
import com.agentic.android.InfoBadge
import com.agentic.android.ResponsiveLayout
import com.agentic.android.SectionCard
import com.agentic.android.StudioEmail
import com.agentic.android.StudioOwner
import com.agentic.android.StudioPhone
import com.agentic.android.appTechnologyTypes
import com.agentic.android.liveSiteProjects
import com.agentic.android.offerPackages
import java.util.Locale

private enum class AssistantStage { BusinessType, Goal, Audience, Budget, Timeline, Completed }
private enum class Speaker { Assistant, User }

private data class AssistantMessage(val id: Int, val speaker: Speaker, val text: String)

private data class AssistantProfile(
    val businessType: String = "",
    val goal: String = "",
    val audience: String = "",
    val budget: String = "",
    val timeline: String = ""
)

private data class Recommendation(
    val requestType: BuildRequestType,
    val packageTitle: String,
    val buildPathTitle: String,
    val rationale: String
)

private data class AssistantTurn(
    val messages: List<String>,
    val nextStage: AssistantStage,
    val updatedProfile: AssistantProfile,
    val recommendation: Recommendation? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun AssistantScreen(
    layout: ResponsiveLayout,
    onExploreCategories: () -> Unit,
    onOpenGuide: () -> Unit,
    onOpenShowcase: () -> Unit,
    onOpenRequest: (BuildRequestType) -> Unit
) {
    val context = LocalContext.current
    val messages = remember { mutableStateListOf<AssistantMessage>() }
    val listState = rememberLazyListState()
    var nextMessageId by remember { mutableIntStateOf(0) }
    var input by rememberSaveable { mutableStateOf("") }
    var stageName by rememberSaveable { mutableStateOf(AssistantStage.BusinessType.name) }
    var profile by remember { mutableStateOf(AssistantProfile()) }
    var voiceRepliesEnabled by rememberSaveable { mutableStateOf(true) }
    var latestRecommendation by remember { mutableStateOf<Recommendation?>(null) }
    var assistantStatus by remember { mutableStateOf("Voice-ready onboarding assistant") }
    var textToSpeechReady by remember { mutableStateOf(false) }
    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }

    fun appendMessage(speaker: Speaker, text: String) {
        messages += AssistantMessage(nextMessageId, speaker, text)
        nextMessageId += 1
    }

    fun speakReply(text: String) {
        if (voiceRepliesEnabled && textToSpeechReady) {
            ttsEngine?.speak(text.take(380), TextToSpeech.QUEUE_FLUSH, Bundle(), "assistant-${System.currentTimeMillis()}")
        }
    }

    fun pushAssistantReply(text: String) {
        appendMessage(Speaker.Assistant, text)
        speakReply(text)
    }

    fun handleUserInput(rawInput: String) {
        val trimmed = rawInput.trim()
        if (trimmed.isBlank()) return
        appendMessage(Speaker.User, trimmed)
        val turn = nextAssistantTurn(trimmed, AssistantStage.valueOf(stageName), profile)
        profile = turn.updatedProfile
        stageName = turn.nextStage.name
        latestRecommendation = turn.recommendation ?: latestRecommendation
        turn.messages.forEach { pushAssistantReply(it) }
    }

    val voiceInputLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
        if (!spokenText.isNullOrBlank()) {
            assistantStatus = "Heard: $spokenText"
            handleUserInput(spokenText)
        } else {
            assistantStatus = "No speech detected"
            pushAssistantReply("I didn't catch that clearly. Try again or type your answer below.")
        }
    }

    fun launchSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk to the Adaryus assistant")
            putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        runCatching {
            voiceInputLauncher.launch(intent)
            assistantStatus = "Listening for your answer..."
        }.getOrElse {
            if (it is ActivityNotFoundException) {
                pushAssistantReply("Speech input is not available on this device yet, but you can still type and chat here.")
            } else {
                throw it
            }
        }
    }

    val micPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            launchSpeechInput()
        } else {
            assistantStatus = "Microphone permission denied"
            pushAssistantReply("Microphone access was denied, so I switched back to text chat. You can still answer the onboarding questions here.")
        }
    }

    fun startVoiceInput() {
        when (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)) {
            PackageManager.PERMISSION_GRANTED -> launchSpeechInput()
            else -> micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    DisposableEffect(context) {
        lateinit var localTts: TextToSpeech
        localTts = TextToSpeech(context) { status ->
            textToSpeechReady = status == TextToSpeech.SUCCESS
            if (status == TextToSpeech.SUCCESS) {
                localTts.language = Locale.US
                localTts.setSpeechRate(0.98f)
                localTts.setPitch(1.0f)
            }
        }
        ttsEngine = localTts
        onDispose {
            localTts.stop()
            localTts.shutdown()
            ttsEngine = null
            textToSpeechReady = false
        }
    }

    LaunchedEffect(Unit) {
        if (messages.isEmpty()) {
            pushAssistantReply("I'm your Adaryus AI concierge. I can onboard new clients, answer common questions, and recommend the best service path based on what you need.")
            pushAssistantReply("Let's start simple. What kind of business, project, or idea are you building right now?")
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.SmartToy, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Adaryus AI Concierge", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(assistantStatus, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Text(
                    text = "This release now opens like a voice-capable onboarding assistant, then stays useful for service guidance, portfolio questions, build-path advice, and the next sales step.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoBadge("Chat-first launch")
                    InfoBadge("Voice input")
                    InfoBadge("Spoken replies")
                    InfoBadge("Service matching")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = voiceRepliesEnabled,
                        onClick = { voiceRepliesEnabled = !voiceRepliesEnabled },
                        label = { Text(if (voiceRepliesEnabled) "Voice replies on" else "Voice replies off") },
                        leadingIcon = {
                            Icon(
                                if (voiceRepliesEnabled) Icons.AutoMirrored.Outlined.VolumeUp else Icons.AutoMirrored.Outlined.VolumeOff,
                                contentDescription = null
                            )
                        }
                    )
                    FilterChip(
                        selected = true,
                        onClick = { },
                        enabled = false,
                        label = { Text(if (textToSpeechReady) "TTS ready" else "TTS loading") },
                        leadingIcon = { Icon(Icons.Outlined.AutoAwesome, contentDescription = null) }
                    )
                }
            }
        }

        latestRecommendation?.let { recommendation ->
            RecommendationCard(
                recommendation = recommendation,
                onOpenGuide = onOpenGuide,
                onOpenRequest = { onOpenRequest(recommendation.requestType) }
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Conversation", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(messages, key = { it.id }) { message ->
                        MessageBubble(message = message)
                    }
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = { handleUserInput("I need more leads for my business") }) {
                        Icon(Icons.Outlined.TipsAndUpdates, contentDescription = null)
                        Text("Need leads")
                    }
                    OutlinedButton(onClick = { handleUserInput("Recommend the best service for me") }) {
                        Icon(Icons.Outlined.AutoAwesome, contentDescription = null)
                        Text("Recommend service")
                    }
                    OutlinedButton(onClick = onExploreCategories) {
                        Icon(Icons.Outlined.Category, contentDescription = null)
                        Text("Categories")
                    }
                    OutlinedButton(onClick = onOpenGuide) {
                        Icon(Icons.Outlined.TravelExplore, contentDescription = null)
                        Text("Build paths")
                    }
                    OutlinedButton(onClick = onOpenShowcase) {
                        Icon(Icons.AutoMirrored.Outlined.OpenInNew, contentDescription = null)
                        Text("Live showcase")
                    }
                    OutlinedButton(
                        onClick = { onOpenRequest(latestRecommendation?.requestType ?: BuildRequestType.ProductivityBusiness) }
                    ) {
                        Icon(Icons.Outlined.Email, contentDescription = null)
                        Text("Open brief")
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.97f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.16f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Type your answer or ask a question") },
                    placeholder = { Text("Tell me what you want to build, what you sell, or what you need help with") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )
                if (layout.compactWidth) {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                handleUserInput(input)
                                input = ""
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = null)
                            Text("Send")
                        }
                        OutlinedButton(onClick = { startVoiceInput() }, modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Outlined.Mic, contentDescription = null)
                            Text("Talk live")
                        }
                    }
                } else {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                handleUserInput(input)
                                input = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = null)
                            Text("Send")
                        }
                        OutlinedButton(onClick = { startVoiceInput() }, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Outlined.Mic, contentDescription = null)
                            Text("Talk live")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(message: AssistantMessage) {
    val isAssistant = message.speaker == Speaker.Assistant
    val bubbleBrush = if (isAssistant) {
        Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.10f)
            )
        )
    } else {
        Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.90f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.78f)
            )
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isAssistant) Arrangement.Start else Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(if (isAssistant) 0.92f else 0.82f)
                .background(bubbleBrush, RoundedCornerShape(22.dp))
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = if (isAssistant) "ADARYUS AI" else "YOU",
                style = MaterialTheme.typography.labelSmall,
                color = if (isAssistant) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecommendationCard(
    recommendation: Recommendation,
    onOpenGuide: () -> Unit,
    onOpenRequest: () -> Unit
) {
    SectionCard(title = "Recommended Next Move") {
        Text(
            text = recommendation.rationale,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoBadge(recommendation.requestType.label)
            InfoBadge(recommendation.packageTitle)
            InfoBadge(recommendation.buildPathTitle)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onOpenGuide, modifier = Modifier.weight(1f)) {
                Text("Compare build paths")
            }
            Button(onClick = onOpenRequest, modifier = Modifier.weight(1f)) {
                Text("Open build brief")
            }
        }
    }
}

private fun nextAssistantTurn(
    input: String,
    stage: AssistantStage,
    profile: AssistantProfile
): AssistantTurn {
    detectGlobalReply(input, profile)?.let { reply ->
        return AssistantTurn(
            messages = reply,
            nextStage = stage,
            updatedProfile = profile,
            recommendation = if (stage == AssistantStage.Completed) recommend(profile) else null
        )
    }

    return when (stage) {
        AssistantStage.BusinessType -> {
            val updated = profile.copy(businessType = input)
            AssistantTurn(
                messages = listOf(
                    "Perfect. What is the main outcome you want this app or website to create for the business: more leads, bookings, sales, retention, automation, or something else?"
                ),
                nextStage = AssistantStage.Goal,
                updatedProfile = updated
            )
        }

        AssistantStage.Goal -> {
            val updated = profile.copy(goal = input)
            AssistantTurn(
                messages = listOf(
                    "Who is the exact audience or customer you want this to persuade, serve, or convert?"
                ),
                nextStage = AssistantStage.Audience,
                updatedProfile = updated
            )
        }

        AssistantStage.Audience -> {
            val updated = profile.copy(audience = input)
            AssistantTurn(
                messages = listOf(
                    "What budget range feels closest right now: exploring, starter, growth, or premium?"
                ),
                nextStage = AssistantStage.Budget,
                updatedProfile = updated
            )
        }

        AssistantStage.Budget -> {
            val updated = profile.copy(budget = input)
            AssistantTurn(
                messages = listOf(
                    "What timeline are you aiming for: ASAP, 30 to 45 days, 60 to 90 days, or strategy first?"
                ),
                nextStage = AssistantStage.Timeline,
                updatedProfile = updated
            )
        }

        AssistantStage.Timeline -> {
            val updated = profile.copy(timeline = input)
            val recommendation = recommend(updated)
            AssistantTurn(
                messages = buildCompletedReply(recommendation),
                nextStage = AssistantStage.Completed,
                updatedProfile = updated,
                recommendation = recommendation
            )
        }

        AssistantStage.Completed -> {
            val recommendation = recommend(profile)
            AssistantTurn(
                messages = listOf(buildCompletedFollowUp(input, profile, recommendation)),
                nextStage = AssistantStage.Completed,
                updatedProfile = profile,
                recommendation = recommendation
            )
        }
    }
}

private fun detectGlobalReply(
    input: String,
    profile: AssistantProfile
): List<String>? {
    val normalized = input.lowercase(Locale.US)

    return when {
        containsAny(normalized, "email", "mail", "contact", "reach you") -> listOf(
            "You can reach $StudioOwner at $StudioEmail or call $StudioPhone. If you want, I can also point you straight into the build brief."
        )

        containsAny(normalized, "phone", "call", "number", "text you") -> listOf(
            "The direct phone number for Adaryus is $StudioPhone. Email is $StudioEmail if you want to send project details."
        )

        containsAny(normalized, "website", "portfolio", "site", "live site", "showcase", "adaryus.com", "advertisewv") -> listOf(
            "Your live web footprint includes ${liveSiteProjects.first().domain}, advertisewv.com, and other portfolio projects in the showcase tab. Open the showcase here to preview them inside the app."
        )

        containsAny(normalized, "service", "offer", "what do you do", "what do you offer") -> listOf(
            "Adaryus is positioned around revenue-oriented builds: business apps, lead funnels, premium mobile experiences, portfolio sites, and cross-platform launches shaped to increase trust, inquiries, bookings, or repeat use."
        )

        containsAny(normalized, "package", "price", "pricing", "budget", "cost") -> listOf(
            "The current engagement models are ${offerPackages.joinToString { it.title }}. Starter is lean, Growth is the balanced sales-system path, and Premium is the flagship direction. If you share your goal, I can recommend the best one."
        )

        containsAny(normalized, "native", "web app", "cross-platform", "pwa", "hybrid", "build path") -> listOf(
            "The main build paths in the app are ${appTechnologyTypes.joinToString { it.title }}. I usually recommend web for fast lead capture, native for top-end device polish, and cross-platform when Android plus iPhone matters."
        )

        containsAny(normalized, "voice", "tts", "talk", "microphone", "speech") -> listOf(
            "This version can listen through Android speech recognition and answer back with built-in text-to-speech, so the onboarding flow works like a lightweight live concierge."
        )

        containsAny(normalized, "recommend", "suggest", "best option", "best service") && profile.businessType.isBlank() -> listOf(
            "I can recommend a service path, but first tell me what kind of business or project you are building so I can match it properly."
        )

        else -> null
    }
}

private fun buildCompletedReply(recommendation: Recommendation): List<String> = listOf(
    "Thanks. Based on what you shared, I would steer this toward ${recommendation.requestType.label}, package ${recommendation.packageTitle}, and a ${recommendation.buildPathTitle.lowercase(Locale.US)} delivery path.",
    recommendation.rationale,
    "You can keep chatting with me, compare build paths, open the showcase, or jump straight into a qualified build brief."
)

private fun buildCompletedFollowUp(
    input: String,
    profile: AssistantProfile,
    recommendation: Recommendation
): String {
    val normalized = input.lowercase(Locale.US)
    return when {
        containsAny(normalized, "why", "reason", "explain") -> recommendation.rationale
        containsAny(normalized, "change", "different", "update") -> "If the goal, budget, or platform changed, I would recalculate from there. Right now your strongest direction is ${recommendation.requestType.label} with ${recommendation.packageTitle}."
        containsAny(normalized, "summary", "recap") -> "You described ${profile.businessType}, aiming for ${profile.goal}, serving ${profile.audience}, with ${profile.budget} budget expectations and a ${profile.timeline} timeline. My recommendation stays ${recommendation.requestType.label} plus ${recommendation.buildPathTitle}."
        else -> "I can keep helping from here. Ask about pricing direction, build paths, live sites, contact info, or tell me more and I will refine the recommendation."
    }
}

private fun recommend(profile: AssistantProfile): Recommendation {
    val combined = listOf(
        profile.businessType,
        profile.goal,
        profile.audience,
        profile.budget,
        profile.timeline
    ).joinToString(" ")

    val requestType = recommendRequestType(combined)
    val packageTitle = recommendPackage(combined)
    val buildPathTitle = recommendBuildPath(combined)
    val rationale = "Because you described ${profile.businessType.ifBlank { "a business need" }} focused on ${profile.goal.ifBlank { "growth" }}, I would prioritize ${requestType.label}, keep the commercial scope at ${packageTitle}, and deliver it through ${buildPathTitle.lowercase(Locale.US)} so the budget, timeline, and sales outcome stay aligned."

    return Recommendation(
        requestType = requestType,
        packageTitle = packageTitle,
        buildPathTitle = buildPathTitle,
        rationale = rationale
    )
}

private fun recommendRequestType(text: String): BuildRequestType {
    val normalized = text.lowercase(Locale.US)
    val scores = mapOf(
        BuildRequestType.ProductivityBusiness to keywordScore(
            normalized,
            "lead",
            "booking",
            "business",
            "dashboard",
            "agency",
            "automation",
            "sales",
            "operations",
            "client",
            "crm"
        ),
        BuildRequestType.SocialCommunity to keywordScore(
            normalized,
            "community",
            "member",
            "social",
            "creator",
            "group",
            "network",
            "audience",
            "engagement",
            "fans"
        ),
        BuildRequestType.EntertainmentStreaming to keywordScore(
            normalized,
            "media",
            "video",
            "stream",
            "music",
            "content",
            "watch",
            "reels",
            "podcast",
            "channel"
        ),
        BuildRequestType.FinanceBanking to keywordScore(
            normalized,
            "finance",
            "bank",
            "payment",
            "wallet",
            "insurance",
            "secure",
            "money",
            "ledger",
            "account"
        ),
        BuildRequestType.EducationLearning to keywordScore(
            normalized,
            "course",
            "training",
            "education",
            "learn",
            "school",
            "student",
            "institute",
            "lesson",
            "teach"
        ),
        BuildRequestType.UtilitiesNavigation to keywordScore(
            normalized,
            "map",
            "tool",
            "scanner",
            "utility",
            "navigation",
            "field",
            "route",
            "service app",
            "tracking"
        )
    )

    return scores.maxByOrNull { it.value }?.takeIf { it.value > 0 }?.key
        ?: BuildRequestType.ProductivityBusiness
}

private fun recommendPackage(text: String): String {
    val normalized = text.lowercase(Locale.US)
    return when {
        containsAny(normalized, "premium", "flagship", "high-end", "enterprise", "full build") -> "Premium"
        containsAny(normalized, "growth", "mid", "scale", "agency") -> "Growth"
        containsAny(normalized, "exploring", "starter", "small", "lean", "budget", "test") -> "Starter"
        else -> "Growth"
    }
}

private fun recommendBuildPath(text: String): String {
    val normalized = text.lowercase(Locale.US)
    return when {
        containsAny(normalized, "website", "landing", "seo", "browser", "funnel", "web") -> "Web Apps"
        containsAny(normalized, "android and iphone", "both platforms", "one codebase", "cross-platform", "cross platform") -> "Cross-Platform Apps"
        containsAny(normalized, "premium mobile", "device", "camera", "gps", "native", "highest performance") -> "Native Apps"
        containsAny(normalized, "installed web", "lightweight install", "pwa") -> "Progressive Web Apps"
        else -> "Cross-Platform Apps"
    }
}

private fun keywordScore(text: String, vararg keywords: String): Int =
    keywords.count { keyword -> text.contains(keyword) }

private fun containsAny(text: String, vararg keywords: String): Boolean =
    keywords.any { keyword -> text.contains(keyword) }
