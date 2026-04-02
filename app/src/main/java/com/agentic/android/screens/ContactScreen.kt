package com.agentic.android.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agentic.android.BuildRequestType
import com.agentic.android.ContactRow
import com.agentic.android.InfoBadge
import com.agentic.android.MetricBadge
import com.agentic.android.ResponsiveLayout
import com.agentic.android.ResponsivePair
import com.agentic.android.SectionCard
import com.agentic.android.SectionTitle
import com.agentic.android.StudioAvailability
import com.agentic.android.StudioDeliveryNote
import com.agentic.android.StudioEmail
import com.agentic.android.StudioStyleNote
import com.agentic.android.launchEmailDraft
import com.agentic.android.launchUriIntent

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun RequestScreen(
    layout: ResponsiveLayout,
    selectedRequestType: BuildRequestType,
    onRequestTypeSelected: (BuildRequestType) -> Unit
) {
    val context = LocalContext.current
    var clientName by rememberSaveable { mutableStateOf("") }
    var brandName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var appName by rememberSaveable { mutableStateOf("") }
    var businessGoal by rememberSaveable { mutableStateOf("") }
    var audience by rememberSaveable { mutableStateOf("") }
    var details by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("Sales-Focused Build Brief")

        SectionCard(title = "What This Brief Should Do") {
            Text(
                text = "This is no longer just a design request. It should explain what you sell, who you want to reach, and what action the app needs to generate.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                MetricBadge(value = "Lead", label = "Ready", modifier = Modifier.weight(1f))
                MetricBadge(value = "Offer", label = "Clarity", modifier = Modifier.weight(1f))
            }
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoBadge("Qualify the buyer")
                InfoBadge("Clarify the offer")
                InfoBadge("Focus on conversion")
            }
        }

        SectionCard(title = "Studio Positioning") {
            ContactRow(Icons.Outlined.Email, StudioEmail)
            ContactRow(Icons.Outlined.PhoneAndroid, StudioAvailability)
            ContactRow(Icons.Outlined.Palette, StudioStyleNote)
            ContactRow(Icons.Outlined.Apps, StudioDeliveryNote)

            OutlinedButton(onClick = { launchUriIntent(context, "mailto:$StudioEmail") }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Outlined.Email, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Email The Studio")
            }
        }

        SectionCard(title = "Qualified Project Brief") {
            Text(
                text = "Choose the product lane and tell Adaryus what revenue, trust, or customer action the app should improve.",
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.10f)
                            )
                        ),
                        RoundedCornerShape(18.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = selectedRequestType.label,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = selectedRequestType.hint,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BuildRequestType.entries.forEach { type ->
                    FilterChip(
                        selected = selectedRequestType == type,
                        onClick = { onRequestTypeSelected(type) },
                        label = { Text(type.label) }
                    )
                }
            }

            ResponsivePair(
                stacked = !layout.formTwoColumn,
                first = { modifier ->
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("Your name") },
                        modifier = modifier
                    )
                },
                second = { modifier ->
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = modifier
                    )
                }
            )

            ResponsivePair(
                stacked = !layout.formTwoColumn,
                first = { modifier ->
                    OutlinedTextField(
                        value = brandName,
                        onValueChange = { brandName = it },
                        label = { Text("Brand or company") },
                        modifier = modifier
                    )
                },
                second = { modifier ->
                    OutlinedTextField(
                        value = appName,
                        onValueChange = { appName = it },
                        label = { Text("App name (optional)") },
                        modifier = modifier
                    )
                }
            )

            ResponsivePair(
                stacked = !layout.formTwoColumn,
                first = { modifier ->
                    OutlinedTextField(
                        value = businessGoal,
                        onValueChange = { businessGoal = it },
                        label = { Text("Main business goal") },
                        placeholder = { Text("More leads, more bookings, more sales, stronger retention") },
                        modifier = modifier
                    )
                },
                second = { modifier ->
                    OutlinedTextField(
                        value = audience,
                        onValueChange = { audience = it },
                        label = { Text("Best customer or audience") },
                        placeholder = { Text("Who should this app persuade or serve?") },
                        modifier = modifier
                    )
                }
            )

            OutlinedTextField(
                value = details,
                onValueChange = { details = it },
                label = { Text("What should the app do and why would someone buy?") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 7
            )

            statusMessage?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (it.startsWith("Ready")) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                            else MaterialTheme.colorScheme.error.copy(alpha = 0.14f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (it.startsWith("Ready")) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                    )
                }
            }

            Button(
                onClick = {
                    val error = validateBuildBrief(clientName, brandName, email, businessGoal, audience, details)
                    if (error != null) {
                        statusMessage = error
                    } else {
                        val body = buildString {
                            appendLine("Build Type: ${selectedRequestType.label}")
                            appendLine("Client Name: $clientName")
                            appendLine("Email: $email")
                            appendLine("Brand / Company: $brandName")
                            if (appName.isNotBlank()) appendLine("App Name: $appName")
                            appendLine("Main Business Goal: $businessGoal")
                            appendLine("Target Audience: $audience")
                            appendLine()
                            appendLine("Project Notes:")
                            appendLine(details.trim())
                        }
                        val opened = launchEmailDraft(
                            context = context,
                            subject = "${selectedRequestType.emailSubject} from Adaryus Builds Apps",
                            body = body
                        )
                        statusMessage = if (opened) {
                            "Ready to send: your email app opened with a more qualified sales-focused brief."
                        } else {
                            "No email app was available. Please send the same details to $StudioEmail."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open Qualified Build Brief Email")
            }
        }
    }
}

private fun validateBuildBrief(
    clientName: String,
    brandName: String,
    email: String,
    businessGoal: String,
    audience: String,
    details: String
): String? {
    if (clientName.trim().length < 3) return "Please enter your name."
    if (brandName.trim().length < 2) return "Please enter your brand or company."
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Please enter a valid email address."
    if (businessGoal.trim().length < 8) return "Please describe the main business goal."
    if (audience.trim().length < 4) return "Please describe the target audience."
    if (details.trim().length < 24) return "Please add more detail about what the app should do and how it should help sales."
    return null
}
