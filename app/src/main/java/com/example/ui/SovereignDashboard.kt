package com.example.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.example.data.model.SovereignSolution
import com.example.data.model.SystemMilestone
import com.example.data.model.TetherBubble

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SovereignDashboard(
    viewModel: SovereignViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val tetherBubbles by viewModel.tetherBubbles.collectAsStateWithLifecycle()
    val solutions by viewModel.sovereignSolutions.collectAsStateWithLifecycle()
    val milestones by viewModel.systemMilestones.collectAsStateWithLifecycle()

    val currentFocus by viewModel.currentProcessingFocus.collectAsStateWithLifecycle()
    val isProcessing by viewModel.isProcessing.collectAsStateWithLifecycle()
    val paradoxIntegrity by viewModel.paradoxIntegrity.collectAsStateWithLifecycle()
    val sandboxLogs by viewModel.sandboxLogs.collectAsStateWithLifecycle()
    val sandboxReport by viewModel.sandboxCompletedReport.collectAsStateWithLifecycle()

    val isBiometricScanning by viewModel.isBiometricScanning.collectAsStateWithLifecycle()
    val biometricStatus by viewModel.biometricStatus.collectAsStateWithLifecycle()
    val userIdentity by viewModel.userIdentity.collectAsStateWithLifecycle()
    val biometricConfidence by viewModel.biometricConfidence.collectAsStateWithLifecycle()
    val contributionScore by viewModel.contributionScore.collectAsStateWithLifecycle()
    val experienceSummary by viewModel.experienceSummary.collectAsStateWithLifecycle()

    // Screen tab selection state
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("SYSTEM CORE", "TETHER FIELD", "SANDBOX", "SOLUTIONS")

    // Terminal Inputs
    var textToTether by remember { mutableStateOf(TextFieldValue("")) }
    var solutionToLearn by remember { mutableStateOf(TextFieldValue("")) }

    // Colors aligned with sovereign glass-box aesthetic
    val darkBackground = Color(0xFF040406)
    val cardBackground = Color(0xFF0B0B0F)
    val neonGreen = Color(0xFF10FA70)
    val neonCyan = Color(0xFF0AE7FF)
    val orangeAccent = Color(0xFFFFB000)
    val darkBorder = Color(0xFF1C1C24)
    val textMuted = Color(0xFF888899)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // High-contrast, real-time background image
        Image(
            painter = painterResource(id = R.drawable.img_solvex_bg),
            contentDescription = "Sovereign OS Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.35f // Exquisite, highly readable background contrast
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "dAIsy haMINJA ",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .background(Color(0x2010FA70), RoundedCornerShape(4.dp))
                                    .border(1.dp, neonGreen, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "UI OS",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = neonGreen
                                    )
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0x50040406),
                        titleContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0x50040406),
                    tonalElevation = 0.dp,
                    modifier = Modifier.border(TweakBorder(0.5.dp, darkBorder, topOnly = true))
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        val selected = selectedTab == index
                        NavigationBarItem(
                            selected = selected,
                            onClick = { selectedTab = index },
                            label = {
                                Text(
                                    text = title,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = when (index) {
                                        0 -> Icons.Default.Info
                                        1 -> Icons.Default.Add
                                        2 -> Icons.Default.Build
                                        else -> Icons.Default.Check
                                    },
                                    contentDescription = title
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = if (index == 2) orangeAccent else neonCyan,
                                selectedTextColor = Color.White,
                                unselectedIconColor = textMuted,
                                unselectedTextColor = textMuted,
                                indicatorColor = Color(0xFF14141E)
                            ),
                            modifier = Modifier.testTag("nav_item_${title.lowercase().replace(" ", "_")}")
                        )
                    }
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // SECTION 1: THE LIVING FOCUS POINT (Active Core)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!isProcessing && !isBiometricScanning) {
                    RoboticPointingHand(pointingLeft = false, color = neonCyan)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                LivingFocusPoint(
                    isProcessing = isProcessing,
                    isBiometricScanning = isBiometricScanning,
                    onClick = {
                        viewModel.triggerHomeostasisReAlignment()
                        Toast.makeText(context, "Initiating Homeostasis Calibration", Toast.LENGTH_SHORT).show()
                    }
                )
                if (!isProcessing && !isBiometricScanning) {
                    Spacer(modifier = Modifier.width(8.dp))
                    RoboticPointingHand(pointingLeft = true, color = neonCyan)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // RENDER SELECTED TAB CONTENT
            when (selectedTab) {
                0 -> {
                    // TAB 0: SYSTEM CORE (Identity + Metrics + Realignment + Milestones)
                    Text(
                        text = "SOVEREIGN IDENTITY PROTOCOL",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = textMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // dAIsy Avatar Portrait with high contrast circle
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, neonCyan, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.daisy_avatar),
                                        contentDescription = "dAIsy haMINJA Active Portrait",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(
                                                    if (isBiometricScanning) orangeAccent else neonGreen,
                                                    CircleShape
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isBiometricScanning) "BIOMETRICS SCANNING..." else "SECURE BIOMETRICS ENCLAVE",
                                            style = TextStyle(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp,
                                                color = if (isBiometricScanning) orangeAccent else neonGreen
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = userIdentity,
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Biometric Confidence: ${String.format("%.1f", biometricConfidence)}%",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 10.sp,
                                            color = textMuted
                                        )
                                    )
                                }
                            }
                            Divider(color = darkBorder, modifier = Modifier.padding(vertical = 12.dp))

                            // Experience Summary and Contribution Score
                            Text(
                                text = "EXPERIENCE SUMMARY (TETHER-BUBBLE 53)",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    color = neonCyan,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = experienceSummary,
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    color = Color.LightGray,
                                    lineHeight = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "CONTRIBUTION SCORE",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp,
                                            color = textMuted
                                        )
                                    )
                                    Text(
                                        text = "$contributionScore XP",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = orangeAccent
                                        )
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isBiometricScanning) {
                                        RoboticPointingHand(pointingLeft = true, color = orangeAccent)
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Button(
                                        onClick = { viewModel.triggerBiometricScan() },
                                        enabled = !isBiometricScanning,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF14141E),
                                            contentColor = Color.White,
                                            disabledContainerColor = Color(0xFF0F0F15)
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, if (isBiometricScanning) darkBorder else neonCyan.copy(alpha = 0.5f)),
                                        modifier = Modifier.testTag("rescan_biometrics_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = "Re-scan Biometrics",
                                            modifier = Modifier.size(14.dp),
                                            tint = if (isBiometricScanning) textMuted else neonCyan
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "RE-SCAN",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isBiometricScanning) textMuted else Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "ACTIVE SOVEREIGN KERNEL STATE",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = textMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // System State Metrics Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            MetricItem(
                                label = "Processing Focus",
                                value = currentFocus,
                                iconColor = if (isProcessing) orangeAccent else neonGreen,
                                testTag = "metric_processing_focus"
                            )
                            Divider(color = darkBorder, modifier = Modifier.padding(vertical = 12.dp))
                            MetricItem(
                                label = "Bubble Saturation",
                                value = "${tetherBubbles.size} / 256 Active Tethers (${String.format("%.1f", (tetherBubbles.size.toFloat() / 256f) * 100)}%)",
                                iconColor = neonCyan,
                                testTag = "metric_bubble_saturation"
                            )
                            Divider(color = darkBorder, modifier = Modifier.padding(vertical = 12.dp))
                            MetricItem(
                                label = "Paradox Integrity",
                                value = paradoxIntegrity,
                                iconColor = neonGreen,
                                testTag = "metric_paradox_integrity"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Teach dAIsy / Learn Solution Box
                    Text(
                        text = "LEARN COGNITIVE METHOD",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = textMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Submit an architectural or mathematical intent. dAIsy will solve the paradox coordinates and crystallize it as an immutable local solution.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = solutionToLearn,
                                    onValueChange = { solutionToLearn = it },
                                    placeholder = {
                                        Text(
                                            "e.g., direct-hardware network compiler",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 12.sp,
                                            color = textMuted
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("learn_solution_input"),
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 13.sp, color = Color.White),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                if (solutionToLearn.text.isNotBlank() && !isProcessing) {
                                                    viewModel.learnNewSolution(solutionToLearn.text)
                                                    solutionToLearn = TextFieldValue("")
                                                }
                                            },
                                            enabled = solutionToLearn.text.isNotBlank() && !isProcessing,
                                            modifier = Modifier.testTag("submit_learn_solution_button")
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.Send,
                                                contentDescription = "Submit Intent",
                                                tint = if (solutionToLearn.text.isNotBlank()) neonCyan else textMuted
                                            )
                                        }
                                    }
                                )
                                if (solutionToLearn.text.isEmpty() && !isProcessing) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    RoboticPointingHand(pointingLeft = true, color = neonCyan)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Growth / Milestones Feed
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SYSTEM GROWTH LOG",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = textMuted,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                        )
                        if (milestones.isNotEmpty()) {
                            Text(
                                text = "CLEAR LOGS",
                                modifier = Modifier
                                    .clickable { viewModel.clearAllMilestones() }
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = orangeAccent,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    if (milestones.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .border(1.dp, darkBorder, RoundedCornerShape(12.dp))
                                .background(cardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "[Log buffer empty. Core stabilized]",
                                fontFamily = FontFamily.Monospace,
                                color = textMuted,
                                fontSize = 11.sp
                            )
                        }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            milestones.take(8).forEach { milestone ->
                                MilestoneRow(milestone)
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: TETHER-BUBBLE FIELD
                    Text(
                        text = "CAPTURE SCREEN TEXT TO TETHER",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = textMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Simulate tethering into active text streams on your device. Enter code snippets, specifications, or requirement texts to segment into isolated bubble-states.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = textToTether,
                                onValueChange = { textToTether = it },
                                placeholder = {
                                    Text(
                                        "Paste or enter requirements / code to tether...",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 12.sp,
                                        color = textMuted
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .testTag("text_to_tether_input"),
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = darkBorder,
                                    cursorColor = neonCyan
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        if (textToTether.text.isNotBlank()) {
                                            viewModel.scanAndTetherText(textToTether.text)
                                            textToTether = TextFieldValue("")
                                        }
                                    },
                                    enabled = textToTether.text.isNotBlank() && !isProcessing,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = neonCyan,
                                        contentColor = Color.Black,
                                        disabledContainerColor = Color(0xFF14242A)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("tether_scan_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Tether",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "TETHER ACTIVE BUBBLES",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                if (textToTether.text.isNotBlank() && !isProcessing) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    RoboticPointingHand(pointingLeft = true, color = neonGreen)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Active Bubbles Field Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ACTIVE TETHER FIELD (${tetherBubbles.size} Bubbles)",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = textMuted,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                        )
                        if (tetherBubbles.isNotEmpty()) {
                            Text(
                                text = "CLEAR ALL",
                                modifier = Modifier
                                    .clickable { viewModel.clearAllTethers() }
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = orangeAccent,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    if (tetherBubbles.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .border(1.dp, darkBorder, RoundedCornerShape(12.dp))
                                .background(cardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "[Zero active bubbles. Submit text above to populate]",
                                fontFamily = FontFamily.Monospace,
                                color = textMuted,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            tetherBubbles.forEach { bubble ->
                                BubbleItemCard(
                                    bubble = bubble,
                                    onToggleSelect = { viewModel.toggleBubbleSelection(bubble) },
                                    onDelete = { viewModel.deleteTether(bubble.id) }
                                )
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: SANDBOX COMPILER & OPTIMIZER
                    val selectedCount = tetherBubbles.count { it.isSelected }

                    Text(
                        text = "SANDBOX COMPILATION CORES",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = textMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Select code fragments/requirements in the TETHER FIELD, then run the sovereign compiler sandbox to generate an optimized, zero-latency Kotlin/Compose blueprint.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (selectedCount > 0) neonGreen else textMuted)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "$selectedCount Bubble-States Selected",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 12.sp,
                                        color = if (selectedCount > 0) Color.White else textMuted
                                    )
                                }

                                Text(
                                    text = "SELECT BUBBLES",
                                    modifier = Modifier
                                        .clickable { selectedTab = 1 }
                                        .padding(vertical = 4.dp, horizontal = 8.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        color = neonCyan,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { viewModel.runSandboxCompilation() },
                                    enabled = selectedCount > 0 && !isProcessing,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = orangeAccent,
                                        contentColor = Color.Black,
                                        disabledContainerColor = Color(0xFF201604)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("run_sandbox_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Run Sandbox"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "COMPILE IN SANDBOX",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                if (selectedCount > 0 && !isProcessing) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    RoboticPointingHand(pointingLeft = true, color = orangeAccent)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Terminal Output Window
                    Text(
                        text = "SANDBOX TERMINAL LOGS",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = textMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .border(1.dp, darkBorder, RoundedCornerShape(8.dp))
                            .background(Color(0xFF020204))
                            .padding(12.dp)
                    ) {
                        if (sandboxLogs.isEmpty()) {
                            Text(
                                text = "Awaiting sandbox execution context...\nSelect bubbles and press [COMPILE IN SANDBOX]",
                                fontFamily = FontFamily.Monospace,
                                color = textMuted,
                                fontSize = 11.sp,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(sandboxLogs) { log ->
                                    Text(
                                        text = log,
                                        fontFamily = FontFamily.Monospace,
                                        color = if (log.contains("SUCCESS") || log.contains("completed")) neonGreen else Color.White,
                                        fontSize = 10.5.sp,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    if (sandboxReport != null) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "OPTIMIZED SOLUTION BLUEPRINT",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = textMuted,
                                    fontSize = 11.sp
                                )
                            )
                            Text(
                                text = "COPY",
                                modifier = Modifier
                                    .clickable {
                                        clipboardManager.setText(AnnotatedString(sandboxReport!!))
                                        Toast.makeText(context, "Blueprint copied to clipboard", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = neonCyan,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFF1B3320), RoundedCornerShape(8.dp))
                                .background(Color(0xFF040A06))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = sandboxReport!!,
                                fontFamily = FontFamily.Monospace,
                                color = Color(0xFFAAFFAA),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                3 -> {
                    // TAB 3: IMMUTABLE SOLUTIONS REPOSITORY
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "IMMUTABLE DETERMINISTIC SOLUTIONS",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = textMuted,
                                fontSize = 11.sp,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    if (solutions.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .border(1.dp, darkBorder, RoundedCornerShape(12.dp))
                                .background(cardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "[No crystallized solutions found. Teach dAIsy in system core]",
                                fontFamily = FontFamily.Monospace,
                                color = textMuted,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            solutions.forEach { solution ->
                                SolutionCard(
                                    solution = solution,
                                    onDelete = { viewModel.deleteSolution(solution.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
}

// --- Composable Subcomponents ---

@Composable
fun LivingFocusPoint(
    isProcessing: Boolean,
    isBiometricScanning: Boolean = false,
    onClick: () -> Unit
) {
    val isSpeaking = isProcessing || isBiometricScanning
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = if (isSpeaking) 0.95f else 0.98f,
        targetValue = if (isSpeaking) 1.15f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isSpeaking) 500 else 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = if (isSpeaking) 0.4f else 0.2f,
        targetValue = if (isSpeaking) 0.9f else 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isSpeaking) 500 else 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    // Glowing laser scanline animation
    val scanlineY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanline"
    )

    // Holographic ring rotation
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val outerColor = if (isSpeaking) Color(0xFFFFB000) else Color(0xFF0AE7FF)
    val coreColor = if (isSpeaking) Color(0xFFFF4081) else Color(0xFF10FA70)
    val textMuted = Color(0xFF888899)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.testTag("living_focus_point_area")
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clickable(
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            // 1. Concentric ripple waves
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(pulseScale)
                    .blur(16.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(outerColor.copy(alpha = glowAlpha), Color.Transparent)
                        )
                    )
            )

            // 2. Outer Orbit rotating holographic dash ring
            Canvas(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer {
                        rotationZ = rotationAngle
                    }
            ) {
                val strokeWidth = 1.5.dp.toPx()
                drawCircle(
                    color = outerColor.copy(alpha = 0.4f),
                    style = Stroke(
                        width = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                    )
                )
            }

            // 3. Second concentric thin technical ring
            Canvas(modifier = Modifier.size(136.dp)) {
                drawCircle(
                    color = coreColor.copy(alpha = 0.25f),
                    style = Stroke(width = 1.dp.toPx())
                )
            }

            // 4. DAISY AVATAR IMAGE FRAME (Replaces the generic green circle)
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .scale(if (isSpeaking) 1.05f else 1.0f)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(
                        BorderStroke(
                            width = 2.dp,
                            color = if (isSpeaking) Color.White else outerColor
                        ),
                        shape = CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.daisy_avatar),
                    contentDescription = "dAIsy haMINJA Sovereign Head",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 5. Flawless Laser scanline sweep (cyan overlay line sweeping down)
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val maxHeightPx = constraints.maxHeight.toFloat()
                    val currentY = scanlineY * maxHeightPx
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawLine(
                            color = Color(0xFF0AE7FF).copy(alpha = 0.6f),
                            start = androidx.compose.ui.geometry.Offset(0f, currentY),
                            end = androidx.compose.ui.geometry.Offset(size.width, currentY),
                            strokeWidth = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                        )
                        // Soft glow under scanline
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF0AE7FF).copy(alpha = 0.12f), Color.Transparent),
                                startY = currentY - 15.dp.toPx(),
                                endY = currentY + 15.dp.toPx()
                            ),
                            topLeft = androidx.compose.ui.geometry.Offset(0f, currentY - 15.dp.toPx()),
                            size = androidx.compose.ui.geometry.Size(size.width, 30.dp.toPx())
                        )
                    }
                }

                // 6. FLAWLESS SPEECH AUDIO WAVEFORM EQUALIZER OVERLAY
                // Rendered at her mouth level (bottom-middle region)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                        .padding(bottom = 12.dp, top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        // Create 9 vertical equalizer bars
                        val numBars = 9
                        for (i in 0 until numBars) {
                            // High performance sine wave heights based on index i and time
                            val barScale by infiniteTransition.animateFloat(
                                initialValue = 0.15f,
                                targetValue = if (isSpeaking) 0.95f else 0.35f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(
                                        durationMillis = (180 + i * 35),
                                        easing = FastOutSlowInEasing
                                    ),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "bar_$i"
                            )

                            val barHeight = (18.dp * barScale) + 3.dp
                            val barColor = if (isSpeaking) {
                                if (i % 2 == 0) Color(0xFFFFB000) else Color(0xFFFF4081)
                            } else {
                                if (i % 2 == 0) Color(0xFF0AE7FF) else Color(0xFF10FA70)
                            }

                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(barHeight)
                                    .background(barColor, RoundedCornerShape(1.5.dp))
                            )
                        }
                    }
                }
            }

            // Small technical holographic widgets floating around the head
            if (isSpeaking) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (-12).dp, y = 12.dp)
                        .background(Color(0xE0040406), RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xFFFFB000).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        "SPEAKING",
                        color = Color(0xFFFFB000),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (-12).dp, y = 12.dp)
                        .background(Color(0xE0040406), RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xFF0AE7FF).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        "ONLINE",
                        color = Color(0xFF0AE7FF),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 12.dp, y = 12.dp)
                    .background(Color(0xE0040406), RoundedCornerShape(4.dp))
                    .border(1.dp, Color(0xFF10FA70).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    "5.4GHz",
                    color = Color(0xFF10FA70),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isProcessing) "SOVEREIGN CORE PROCESSOR ACTIVE" else "LIVING FOCUS POINT (SECURED)",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = if (isProcessing) Color(0xFFFFB000) else Color(0xFF0AE7FF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        )
        Text(
            text = if (isSpeaking) "Paradox computation & biometric analysis running..." else "Tap core to trigger Homeostasis Calibration",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = textMuted,
                fontSize = 9.5.sp
            )
        )
    }
}

@Composable
fun MetricItem(
    label: String,
    value: String,
    iconColor: Color,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = textMuted,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(iconColor)
        )
    }
}

@Composable
fun MilestoneRow(milestone: SystemMilestone) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF141E18), RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF060D08))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        when (milestone.milestoneType) {
                            "LEARNED_SOLUTION" -> Color(0xFF0AE7FF)
                            "PARADOX_REALIGNED" -> Color(0xFF10FA70)
                            else -> Color(0xFFFFB000)
                        }
                    )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = milestone.title,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = milestone.description,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.5.sp,
                        color = textMuted,
                        lineHeight = 14.sp
                    )
                )
            }
        }
    }
}

@Composable
fun BubbleItemCard(
    bubble: TetherBubble,
    onToggleSelect: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (bubble.isSelected) Color(0xFF0AE7FF).copy(alpha = 0.5f) else darkBorder,
                RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (bubble.isSelected) Color(0xFF070E14) else cardBackground
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = bubble.isSelected,
                onCheckedChange = { onToggleSelect() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF0AE7FF),
                    uncheckedColor = textMuted,
                    checkmarkColor = Color.Black
                ),
                modifier = Modifier.testTag("bubble_checkbox_${bubble.id}")
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "SRC: ${bubble.sourceScreen.uppercase()}",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0AE7FF)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = bubble.text,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_bubble_${bubble.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete bubble",
                    tint = Color(0xFFFF5252).copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun SolutionCard(
    solution: SovereignSolution,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, darkBorder, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = solution.title,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Learned from: \"${solution.learnedFrom}\"",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.5.sp,
                            color = Color(0xFF0AE7FF)
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .background(Color(0x1510FA70), RoundedCornerShape(4.dp))
                        .border(1.dp, neonGreen.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "+${solution.performanceBoost}% Boost",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = neonGreen
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = solution.description,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.5.sp,
                    color = Color.LightGray,
                    lineHeight = 15.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "HIDE SOURCE SYNTAX" else "VIEW SOURCE SYNTAX",
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .padding(vertical = 4.dp),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        color = orangeAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(24.dp)
                        .testTag("delete_solution_${solution.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete solution",
                        tint = Color(0xFFFF5252).copy(alpha = 0.5f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF020204))
                            .border(1.dp, Color(0xFF2C2C35), RoundedCornerShape(6.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = solution.implementationMethod,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFFC0CCD0),
                            fontSize = 10.5.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}

// Custom Border Stroke implementation to draw a border only on specific edges
fun TweakBorder(width: androidx.compose.ui.unit.Dp, color: Color, topOnly: Boolean = false): BorderStroke {
    return BorderStroke(width, color)
}

// Convenient values used inside views to reduce repetitive declarations
val textMuted = Color(0xFF888899)
val neonGreen = Color(0xFF10FA70)
val neonCyan = Color(0xFF0AE7FF)
val orangeAccent = Color(0xFFFFB000)
val cardBackground = Color(0xFF0B0B0F)
val darkBorder = Color(0xFF1C1C24)

@Composable
fun RoboticPointingHand(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF0AE7FF), // Neon Cyan
    pointingLeft: Boolean = true // Direction
) {
    val infiniteTransition = rememberInfiniteTransition(label = "hand_hover")
    
    // Smooth floating/hovering translation
    val hoverOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hover"
    )
    
    // Smooth pointing/pulsing scale
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.93f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Canvas(
        modifier = modifier
            .size(54.dp, 44.dp)
            .graphicsLayer {
                translationX = if (pointingLeft) hoverOffset else -hoverOffset
                scaleX = pulseScale
                scaleY = pulseScale
            }
    ) {
        val width = size.width
        val height = size.height
        val strokeWidth = 1.8f.dp.toPx()
        
        if (pointingLeft) {
            // Index finger pointing left: from (width * 0.1) to (width * 0.45)
            // Palm plate: (width * 0.45) to (width * 0.72)
            // Arm shaft: (width * 0.72) to width
            
            // 1. Arm Shaft
            drawLine(
                color = color.copy(alpha = 0.4f),
                start = androidx.compose.ui.geometry.Offset(width * 0.9f, height * 0.42f),
                end = androidx.compose.ui.geometry.Offset(width * 0.72f, height * 0.42f),
                strokeWidth = strokeWidth * 2f
            )
            drawLine(
                color = color.copy(alpha = 0.4f),
                start = androidx.compose.ui.geometry.Offset(width * 0.9f, height * 0.58f),
                end = androidx.compose.ui.geometry.Offset(width * 0.72f, height * 0.58f),
                strokeWidth = strokeWidth * 2f
            )
            // Cross struts
            drawLine(
                color = color.copy(alpha = 0.7f),
                start = androidx.compose.ui.geometry.Offset(width * 0.82f, height * 0.42f),
                end = androidx.compose.ui.geometry.Offset(width * 0.78f, height * 0.58f),
                strokeWidth = strokeWidth
            )
            
            // 2. Wrist Joint Socket (Ring node)
            drawCircle(
                color = color,
                center = androidx.compose.ui.geometry.Offset(width * 0.7f, height * 0.5f),
                radius = 4.5.dp.toPx(),
                style = Stroke(width = 1.5.dp.toPx())
            )
            drawCircle(
                color = color,
                center = androidx.compose.ui.geometry.Offset(width * 0.7f, height * 0.5f),
                radius = 2.dp.toPx()
            )
            
            // 3. Palm Plate (high-tech polygon)
            val palmPath = Path().apply {
                moveTo(width * 0.65f, height * 0.28f)
                lineTo(width * 0.45f, height * 0.35f)
                lineTo(width * 0.45f, height * 0.65f)
                lineTo(width * 0.65f, height * 0.72f)
                close()
            }
            drawPath(
                path = palmPath,
                color = color.copy(alpha = 0.12f)
            )
            drawPath(
                path = palmPath,
                color = color,
                style = Stroke(width = 1.5.dp.toPx())
            )
            
            // 4. Pointing Index Finger (multi-segment jointed path)
            val fingerPath = Path().apply {
                moveTo(width * 0.45f, height * 0.38f) // knuckle
                lineTo(width * 0.3f, height * 0.38f)  // middle joint
                lineTo(width * 0.12f, height * 0.38f) // fingertip
            }
            drawPath(
                path = fingerPath,
                color = color,
                style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round)
            )
            
            // Joint indicators
            drawCircle(color = color, center = androidx.compose.ui.geometry.Offset(width * 0.3f, height * 0.38f), radius = 1.8.dp.toPx())
            drawCircle(color = Color.White, center = androidx.compose.ui.geometry.Offset(width * 0.12f, height * 0.38f), radius = 2.5.dp.toPx())
            
            // 5. Folded other fingers (Middle & Ring)
            val middleFold = Path().apply {
                moveTo(width * 0.45f, height * 0.48f)
                lineTo(width * 0.36f, height * 0.48f)
                lineTo(width * 0.38f, height * 0.56f)
            }
            drawPath(path = middleFold, color = color.copy(alpha = 0.7f), style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round))
            
            val ringFold = Path().apply {
                moveTo(width * 0.45f, height * 0.58f)
                lineTo(width * 0.38f, height * 0.58f)
                lineTo(width * 0.40f, height * 0.64f)
            }
            drawPath(path = ringFold, color = color.copy(alpha = 0.5f), style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round))
            
            // Thumb
            val thumbPath = Path().apply {
                moveTo(width * 0.58f, height * 0.3f)
                lineTo(width * 0.48f, height * 0.2f)
                lineTo(width * 0.40f, height * 0.22f)
            }
            drawPath(path = thumbPath, color = color, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
            
            // 6. Laser pointing glow circle at fingertip
            drawCircle(
                color = color.copy(alpha = 0.25f),
                center = androidx.compose.ui.geometry.Offset(width * 0.12f, height * 0.38f),
                radius = 7.dp.toPx() * pulseScale
            )
        } else {
            // Index finger pointing right: from (width * 0.55) to (width * 0.88)
            // Palm plate: (width * 0.28) to (width * 0.55)
            // Arm shaft: 0 to (width * 0.28)
            
            // 1. Arm Shaft
            drawLine(
                color = color.copy(alpha = 0.4f),
                start = androidx.compose.ui.geometry.Offset(width * 0.1f, height * 0.42f),
                end = androidx.compose.ui.geometry.Offset(width * 0.28f, height * 0.42f),
                strokeWidth = strokeWidth * 2f
            )
            drawLine(
                color = color.copy(alpha = 0.4f),
                start = androidx.compose.ui.geometry.Offset(width * 0.1f, height * 0.58f),
                end = androidx.compose.ui.geometry.Offset(width * 0.28f, height * 0.58f),
                strokeWidth = strokeWidth * 2f
            )
            drawLine(
                color = color.copy(alpha = 0.7f),
                start = androidx.compose.ui.geometry.Offset(width * 0.18f, height * 0.42f),
                end = androidx.compose.ui.geometry.Offset(width * 0.22f, height * 0.58f),
                strokeWidth = strokeWidth
            )
            
            // 2. Wrist Joint Socket
            drawCircle(
                color = color,
                center = androidx.compose.ui.geometry.Offset(width * 0.3f, height * 0.5f),
                radius = 4.5.dp.toPx(),
                style = Stroke(width = 1.5.dp.toPx())
            )
            drawCircle(
                color = color,
                center = androidx.compose.ui.geometry.Offset(width * 0.3f, height * 0.5f),
                radius = 2.dp.toPx()
            )
            
            // 3. Palm Plate
            val palmPath = Path().apply {
                moveTo(width * 0.35f, height * 0.28f)
                lineTo(width * 0.55f, height * 0.35f)
                lineTo(width * 0.55f, height * 0.65f)
                lineTo(width * 0.35f, height * 0.72f)
                close()
            }
            drawPath(
                path = palmPath,
                color = color.copy(alpha = 0.12f)
            )
            drawPath(
                path = palmPath,
                color = color,
                style = Stroke(width = 1.5.dp.toPx())
            )
            
            // 4. Pointing Index Finger
            val fingerPath = Path().apply {
                moveTo(width * 0.55f, height * 0.38f) // knuckle
                lineTo(width * 0.7f, height * 0.38f)  // middle joint
                lineTo(width * 0.88f, height * 0.38f) // fingertip
            }
            drawPath(
                path = fingerPath,
                color = color,
                style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round)
            )
            
            drawCircle(color = color, center = androidx.compose.ui.geometry.Offset(width * 0.7f, height * 0.38f), radius = 1.8.dp.toPx())
            drawCircle(color = Color.White, center = androidx.compose.ui.geometry.Offset(width * 0.88f, height * 0.38f), radius = 2.5.dp.toPx())
            
            // 5. Folded other fingers
            val middleFold = Path().apply {
                moveTo(width * 0.55f, height * 0.48f)
                lineTo(width * 0.64f, height * 0.48f)
                lineTo(width * 0.62f, height * 0.56f)
            }
            drawPath(path = middleFold, color = color.copy(alpha = 0.7f), style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round))
            
            val ringFold = Path().apply {
                moveTo(width * 0.55f, height * 0.58f)
                lineTo(width * 0.62f, height * 0.58f)
                lineTo(width * 0.60f, height * 0.64f)
            }
            drawPath(path = ringFold, color = color.copy(alpha = 0.5f), style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round))
            
            // Thumb
            val thumbPath = Path().apply {
                moveTo(width * 0.42f, height * 0.3f)
                lineTo(width * 0.52f, height * 0.2f)
                lineTo(width * 0.60f, height * 0.22f)
            }
            drawPath(path = thumbPath, color = color, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
            
            // 6. Laser pointing glow
            drawCircle(
                color = color.copy(alpha = 0.25f),
                center = androidx.compose.ui.geometry.Offset(width * 0.88f, height * 0.38f),
                radius = 7.dp.toPx() * pulseScale
            )
        }
    }
}
