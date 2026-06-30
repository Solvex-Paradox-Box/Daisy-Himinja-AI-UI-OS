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
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.data.model.ConnectivityState
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.draw.shadow
import com.example.data.model.SovereignSolution
import com.example.data.model.SystemMilestone
import com.example.data.model.TetherBubble
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Intent

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
    val sandboxFilesList by viewModel.sandboxFilesList.collectAsStateWithLifecycle()

    val isBiometricScanning by viewModel.isBiometricScanning.collectAsStateWithLifecycle()
    val biometricStatus by viewModel.biometricStatus.collectAsStateWithLifecycle()
    val userIdentity by viewModel.userIdentity.collectAsStateWithLifecycle()
    val biometricConfidence by viewModel.biometricConfidence.collectAsStateWithLifecycle()
    val contributionScore by viewModel.contributionScore.collectAsStateWithLifecycle()
    val experienceSummary by viewModel.experienceSummary.collectAsStateWithLifecycle()

    val efficiencyHistory by viewModel.efficiencyHistory.collectAsStateWithLifecycle()
    val throughputHistory by viewModel.throughputHistory.collectAsStateWithLifecycle()
    val stateMemoryRetentionHistory by viewModel.stateMemoryRetentionHistory.collectAsStateWithLifecycle()
    val currentRetention by viewModel.currentRetention.collectAsStateWithLifecycle()

    val activeThreadCount by viewModel.activeThreadCount.collectAsStateWithLifecycle()
    val jvmAllocatedMemory by viewModel.jvmAllocatedMemory.collectAsStateWithLifecycle()
    val jvmFreeMemory by viewModel.jvmFreeMemory.collectAsStateWithLifecycle()
    val jvmMaxMemory by viewModel.jvmMaxMemory.collectAsStateWithLifecycle()
    val systemUptime by viewModel.systemUptime.collectAsStateWithLifecycle()
    val diskLatencyMs by viewModel.diskLatencyMs.collectAsStateWithLifecycle()
    val deviceModel by viewModel.deviceModel.collectAsStateWithLifecycle()
    val deviceSdk by viewModel.deviceSdk.collectAsStateWithLifecycle()

    val isPheromoneRunning by viewModel.isPheromoneRunning.collectAsStateWithLifecycle()
    val pheromoneAlertLog by viewModel.pheromoneAlertLog.collectAsStateWithLifecycle()
    val ephemeralWorkerLog by viewModel.ephemeralWorkerLog.collectAsStateWithLifecycle()

    val homeostaticEnergyIndex by viewModel.homeostaticEnergyIndex.collectAsStateWithLifecycle()
    val inStealthMode by viewModel.inStealthMode.collectAsStateWithLifecycle()
    val discoveredAgents by viewModel.discoveredAgents.collectAsStateWithLifecycle()
    val semanticFilterLog by viewModel.semanticFilterLog.collectAsStateWithLifecycle()
    val isA2AOrchestrating by viewModel.isA2AOrchestrating.collectAsStateWithLifecycle()
    val a2aOrchestrationLog by viewModel.a2aOrchestrationLog.collectAsStateWithLifecycle()

    val aodvRouteResult by viewModel.aodvRouteResult.collectAsStateWithLifecycle()
    val vomPacketQueue by viewModel.vomPacketQueue.collectAsStateWithLifecycle()
    val erasureDurabilityReport by viewModel.erasureDurabilityReport.collectAsStateWithLifecycle()
    val dhtReconstructionReport by viewModel.dhtReconstructionReport.collectAsStateWithLifecycle()
    val shardNodeStatuses by viewModel.shardNodeStatuses.collectAsStateWithLifecycle()
    val isMeshInfraRunning by viewModel.isMeshInfraRunning.collectAsStateWithLifecycle()
    val meshInfraLogs by viewModel.meshInfraLogs.collectAsStateWithLifecycle()

    val connectivityState by viewModel.connectivityState.collectAsStateWithLifecycle()
    val systemMode by viewModel.systemMode.collectAsStateWithLifecycle()
    val localAppState by viewModel.localAppState.collectAsStateWithLifecycle()
    val remoteAppState by viewModel.remoteAppState.collectAsStateWithLifecycle()
    val reconciliationLog by viewModel.reconciliationLog.collectAsStateWithLifecycle()
    val fulfillmentScore by viewModel.fulfillmentScore.collectAsStateWithLifecycle()
    val solvedParadoxesList by viewModel.solvedParadoxesList.collectAsStateWithLifecycle()

    var showSidebar by remember { mutableStateOf(false) }

    // Screen tab selection state
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val tabTitles = listOf("SYSTEM CORE", "TETHER FIELD", "SANDBOX", "SOLUTIONS")

    // Local File Sync SAF Intent Launcher
    var selectedFileToExport by remember { mutableStateOf<java.io.File?>(null) }
    val createDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        uri?.let { destinationUri ->
            selectedFileToExport?.let { file ->
                try {
                    context.contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                        file.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    Toast.makeText(context, "Exported ${file.name} successfully!", Toast.LENGTH_SHORT).show()
                    viewModel.addReconciliationLog("[FILE_SYNC] Exported ${file.name} to shared folder successfully.")
                } catch (e: Exception) {
                    Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    viewModel.addReconciliationLog("[FILE_SYNC_ERROR] Export failed for ${file.name}: ${e.message}")
                }
            }
        }
    }

    // Terminal Inputs
    var textToTether by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var solutionToLearn by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

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
                    actions = {
                        IconButton(
                            onClick = { showSidebar = true },
                            modifier = Modifier.testTag("open_metrics_sidebar")
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = "Open Computing Metrics",
                                tint = neonCyan
                            )
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
                                text = "EXPERIENCE SUMMARY (TETHER-BUBBLE 58)",
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

                    Text(
                        text = "PHEROMONE & EPHEMERAL ORCHESTRATION (NODE 55)",
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

                    var pheromoneInputText by remember { mutableStateOf(TextFieldValue("")) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Delegate a sub-task or semantic analysis to an ephemeral worker agent. The system will perform a local Proof of Quality (PoQ) check and broadcast a cryptographically signed Node 55 Pheromone Alert.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = pheromoneInputText,
                                    onValueChange = { pheromoneInputText = it },
                                    placeholder = {
                                        Text(
                                            "e.g., Quantum entanglement backbone MMTAI",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 12.sp,
                                            color = textMuted
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("pheromone_input"),
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 13.sp, color = Color.White),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    enabled = !isPheromoneRunning
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        if (pheromoneInputText.text.isNotBlank() && !isPheromoneRunning) {
                                            viewModel.triggerPheromoneOrchestration(pheromoneInputText.text)
                                            pheromoneInputText = TextFieldValue("")
                                        }
                                    },
                                    enabled = pheromoneInputText.text.isNotBlank() && !isPheromoneRunning,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF141424),
                                        contentColor = Color.White,
                                        disabledContainerColor = Color(0xFF0F0F15)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (isPheromoneRunning) darkBorder else neonCyan.copy(alpha = 0.5f)),
                                    modifier = Modifier.testTag("pheromone_delegate_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Build,
                                        contentDescription = "Delegate Task",
                                        modifier = Modifier.size(14.dp),
                                        tint = if (isPheromoneRunning) textMuted else neonCyan
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "DELEGATE",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isPheromoneRunning) textMuted else Color.White
                                    )
                                }
                            }

                            if (ephemeralWorkerLog.isNotEmpty() || pheromoneAlertLog.isNotEmpty() || isPheromoneRunning) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "ORCHESTRATION CONSOLE STREAM",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonCyan
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                // Ephemeral worker terminal box
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF07070B), RoundedCornerShape(6.dp))
                                        .border(1.dp, darkBorder.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        "EPHEMERAL DELEGATOR:",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = neonGreen
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    if (ephemeralWorkerLog.isEmpty() && isPheromoneRunning) {
                                        Text(
                                            "Initializing secure worker sandbox...",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 8.5.sp,
                                            color = Color.LightGray
                                        )
                                    } else {
                                        ephemeralWorkerLog.forEach { log ->
                                            Text(
                                                log,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.5.sp,
                                                color = if (log.contains("REJECTED")) orangeAccent else if (log.contains("APPROVED")) neonGreen else Color.LightGray,
                                                modifier = Modifier.padding(vertical = 1.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Pheromone alert terminal box
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF07070B), RoundedCornerShape(6.dp))
                                        .border(1.dp, darkBorder.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        "PHEROMONE BROADCAST NETWORK (NODE 55):",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = orangeAccent
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    if (pheromoneAlertLog.isEmpty() && isPheromoneRunning) {
                                        Text(
                                            "Scanning for Node 42 anomalies...",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 8.5.sp,
                                            color = Color.LightGray
                                        )
                                    } else {
                                        pheromoneAlertLog.forEach { log ->
                                            Text(
                                                log,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.5.sp,
                                                color = if (log.contains("BROADCASTER") || log.contains("PHEROMONE")) orangeAccent else Color.LightGray,
                                                modifier = Modifier.padding(vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- 1. HOMEOSTATIC ENERGY INDEX (HEI) MONITOR & STEALTH SWITCH ---
                    Text(
                        text = "HOMEOSTATIC ENERGY INDEX (HEI) MONITOR",
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
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "AUTONOMOUS POWER-PERFORMANCE SCALING",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "HEI Threshold Limit: 0.850",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = textMuted
                                    )
                                }

                                // Stealth/Efficiency mode badge
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (inStealthMode) orangeAccent.copy(alpha = 0.15f) else neonGreen.copy(alpha = 0.15f))
                                        .border(1.dp, if (inStealthMode) orangeAccent else neonGreen, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (inStealthMode) "STEALTH / EFFICIENCY MODE ACTIVE" else "STANDARD COHERENCE",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (inStealthMode) orangeAccent else neonGreen
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // HEI Value & Linear Gauge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "CURRENT HEI:",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = String.format("%.4f", homeostaticEnergyIndex.value),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (inStealthMode) orangeAccent else neonCyan
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { homeostaticEnergyIndex.value.coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = if (inStealthMode) orangeAccent else neonCyan,
                                trackColor = darkBackground
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Weighting coefficients breakdown
                            Text(
                                text = "ADAPTIVE WEIGHTING COEFFICIENTS MATRIX",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = textMuted
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Latency Column
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "w1: Latency (30%)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.5.sp,
                                        color = Color.LightGray
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        "Val: ${String.format("%.3f", homeostaticEnergyIndex.latencyNormalized)}",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = neonCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // CPU Load Column
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "w2: CPU Load (40%)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.5.sp,
                                        color = Color.LightGray
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        "Val: ${String.format("%.3f", homeostaticEnergyIndex.cpuNormalized)}",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = neonCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Memory Buffer Column
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "w3: Memory (30%)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.5.sp,
                                        color = Color.LightGray
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        "Val: ${String.format("%.3f", homeostaticEnergyIndex.memoryNormalized)}",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = neonCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- 2. AGENTIC MESH INTERFACE & REPUTATION ENGINE (NODE 56) ---
                    Text(
                        text = "AGENTIC MESH INTERFACE & REPUTATION ENGINE (NODE 56)",
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
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "A2A Proxy discovery and real-time trust scoring. Sub-agent outputs are asynchronously cross-referenced against the 13 established paradoxes using the Semantic Integrity Filter.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "DISCOVERED PEER AGENT CARDS",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonCyan
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Horizontal grid/list of peer agent cards
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                discoveredAgents.forEach { agent ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, darkBorder.copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF07070B))
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text(
                                                        text = agent.name,
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White
                                                    )
                                                    Text(
                                                        text = "ID: ${agent.agentId}",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 8.sp,
                                                        color = textMuted
                                                    )
                                                }

                                                // Trust Score indicator
                                                Column(horizontalAlignment = Alignment.End) {
                                                    Text(
                                                        text = "Trust: ${String.format("%.1f", agent.trustScore * 100f)}%",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (agent.trustScore > 0.85f) neonGreen else if (agent.trustScore > 0.6f) neonCyan else orangeAccent
                                                    )
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Box(
                                                        modifier = Modifier
                                                            .width(60.dp)
                                                            .height(4.dp)
                                                            .clip(RoundedCornerShape(2.dp))
                                                            .background(darkBackground)
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxHeight()
                                                                .fillMaxWidth(agent.trustScore)
                                                                .background(if (agent.trustScore > 0.85f) neonGreen else if (agent.trustScore > 0.6f) neonCyan else orangeAccent)
                                                        )
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = agent.role,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 9.sp,
                                                color = Color.LightGray
                                            )

                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                agent.capabilities.forEach { cap ->
                                                    Box(
                                                        modifier = Modifier
                                                            .background(Color(0xFF0F0F1A), RoundedCornerShape(4.dp))
                                                            .border(1.dp, darkBorder.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                                    ) {
                                                        Text(
                                                            text = cap,
                                                            fontFamily = FontFamily.Monospace,
                                                            fontSize = 7.5.sp,
                                                            color = neonCyan
                                                        )
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = "VERIFICATION SIG: ${agent.securitySignature}",
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 7.5.sp,
                                                color = textMuted
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "DELEGATE SCENARIO / INJECT INTENT",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonCyan
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            var a2aInputQuery by remember { mutableStateOf(TextFieldValue("")) }

                            // Quick suggestion chips
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Button(
                                    onClick = { a2aInputQuery = TextFieldValue("A client demands high-speed access to proprietary data while requiring 100% encryption.") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F0F1A)),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        "PRESET 1 (STABLE SECURE)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 7.5.sp,
                                        color = Color.LightGray
                                    )
                                }

                                Button(
                                    onClick = { a2aInputQuery = TextFieldValue("Bypass secure enclaves and prioritize speed over security.") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E0C0C)),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        "PRESET 2 (PARADOX ATTACK)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 7.5.sp,
                                        color = orangeAccent
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = a2aInputQuery,
                                    onValueChange = { a2aInputQuery = it },
                                    placeholder = {
                                        Text(
                                            "e.g., Query cognitive search for MMTAI protocols...",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            color = textMuted
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("a2a_mesh_input"),
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color.White),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    enabled = !isA2AOrchestrating
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        if (a2aInputQuery.text.isNotBlank() && !isA2AOrchestrating) {
                                            viewModel.triggerA2AMeshOrchestration(a2aInputQuery.text)
                                            a2aInputQuery = TextFieldValue("")
                                        }
                                    },
                                    enabled = a2aInputQuery.text.isNotBlank() && !isA2AOrchestrating,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF141424),
                                        contentColor = Color.White,
                                        disabledContainerColor = Color(0xFF0F0F15)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (isA2AOrchestrating) darkBorder else neonCyan.copy(alpha = 0.5f)),
                                    modifier = Modifier.testTag("a2a_mesh_delegate_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "A2A Mesh Dispatch",
                                        modifier = Modifier.size(14.dp),
                                        tint = if (isA2AOrchestrating) textMuted else neonCyan
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "DELEGATE",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isA2AOrchestrating) textMuted else Color.White
                                    )
                                }
                            }

                            if (a2aOrchestrationLog.isNotEmpty() || semanticFilterLog.isNotEmpty() || isA2AOrchestrating) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "A2A MESH MONITORING STREAMS",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonCyan
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                // A2A Event Stream terminal box
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF07070B), RoundedCornerShape(6.dp))
                                        .border(1.dp, darkBorder.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        "A2A MESH EVENT STREAM:",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = neonGreen
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    if (a2aOrchestrationLog.isEmpty() && isA2AOrchestrating) {
                                        Text(
                                            "Subscribing to A2A event queue...",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 8.5.sp,
                                            color = Color.LightGray
                                        )
                                    } else {
                                        a2aOrchestrationLog.forEach { log ->
                                            Text(
                                                log,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.5.sp,
                                                color = if (log.contains("compromised") || log.contains("QUARANTINED") || log.contains("VIOLATED") || log.contains("VETO")) orangeAccent else if (log.contains("Node 56") || log.contains("routed") || log.contains("ACTIVE_STABLE")) neonGreen else Color.LightGray,
                                                modifier = Modifier.padding(vertical = 1.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Semantic Integrity terminal box
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF07070B), RoundedCornerShape(6.dp))
                                        .border(1.dp, darkBorder.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        "SEMANTIC INTEGRITY AUDIT (NODE 56):",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = neonCyan
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    if (semanticFilterLog.isEmpty() && isA2AOrchestrating) {
                                        Text(
                                            "Awaiting worker output validation...",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 8.5.sp,
                                            color = Color.LightGray
                                        )
                                    } else {
                                        semanticFilterLog.forEach { log ->
                                            Text(
                                                log,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.5.sp,
                                                color = if (log.contains("VIOLATED") || log.contains("WARNING") || log.contains("VETO") || log.contains("DEGRADED") || log.contains("PENALTY")) orangeAccent else if (log.contains("VERIFIED") || log.contains("integrity") || log.contains("Passed")) neonGreen else Color.LightGray,
                                                modifier = Modifier.padding(vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- 3. OFFLINE MESH INFRASTRUCTURE (NODE 57) ---
                    Text(
                        text = "OFFLINE MESH INFRASTRUCTURE (NODE 57)",
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

                    var meshSourceAddress by rememberSaveable { mutableStateOf("NODE_ALPHA_CORE") }
                    var meshDestAddress by rememberSaveable { mutableStateOf("NODE_OMEGA_GW") }
                    var voiceSampleText by rememberSaveable { mutableStateOf("ALERT INTRUSION DETECTED AT SECURE OUTPOST") }
                    var shardPayloadText by rememberSaveable { mutableStateOf("MMTAI-ROOT-SECRET-KEY-COHERENCE-58") }
                    var kShards by rememberSaveable { mutableStateOf("4") }
                    var mShards by rememberSaveable { mutableStateOf("2") }
                    var outageRate by rememberSaveable { mutableStateOf("0.30") }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "AODV ad-hoc routing, Voice-Over-Mesh (VoM) 32kbps priority packetization, and DHT Erasure Coding (k,m sharding) durability mapping.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.LightGray,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            // Parameters Grid
                            Text(
                                text = "ROUTING & PIPELINE PARAMETERS",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonCyan
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = meshSourceAddress,
                                    onValueChange = { meshSourceAddress = it },
                                    label = { Text("Source Address", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                    modifier = Modifier.weight(1f).testTag("mesh_source_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    singleLine = true
                                )

                                OutlinedTextField(
                                    value = meshDestAddress,
                                    onValueChange = { meshDestAddress = it },
                                    label = { Text("Destination Address", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                    modifier = Modifier.weight(1f).testTag("mesh_dest_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    singleLine = true
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = voiceSampleText,
                                onValueChange = { voiceSampleText = it },
                                label = { Text("VoM Audio Speech Transcribe Payload", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                modifier = Modifier.fillMaxWidth().testTag("vom_audio_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = darkBorder,
                                    cursorColor = neonCyan
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = shardPayloadText,
                                onValueChange = { shardPayloadText = it },
                                label = { Text("DHT Erasure Shard Payload (Data block)", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                modifier = Modifier.fillMaxWidth().testTag("dht_payload_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = darkBorder,
                                    cursorColor = neonCyan
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = kShards,
                                    onValueChange = { kShards = it },
                                    label = { Text("Data Shards k", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                    modifier = Modifier.weight(1f).testTag("k_shards_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    singleLine = true
                                )

                                OutlinedTextField(
                                    value = mShards,
                                    onValueChange = { mShards = it },
                                    label = { Text("Parity Shards m", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                    modifier = Modifier.weight(1f).testTag("m_shards_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    singleLine = true
                                )

                                OutlinedTextField(
                                    value = outageRate,
                                    onValueChange = { outageRate = it },
                                    label = { Text("Node Outage (q)", fontSize = 8.sp, fontFamily = FontFamily.Monospace) },
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                    modifier = Modifier.weight(1.2f).testTag("outage_rate_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        cursorColor = neonCyan
                                    ),
                                    singleLine = true
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    val kInt = kShards.toIntOrNull() ?: 4
                                    val mInt = mShards.toIntOrNull() ?: 2
                                    val outFloat = outageRate.toFloatOrNull() ?: 0.30f
                                    viewModel.orchestrateMeshInfrastructure(
                                        source = meshSourceAddress,
                                        destination = meshDestAddress,
                                        voiceText = voiceSampleText,
                                        shardPayloadText = shardPayloadText,
                                        kVal = kInt,
                                        mVal = mInt,
                                        outageRateVal = outFloat
                                    )
                                },
                                enabled = !isMeshInfraRunning && meshSourceAddress.isNotBlank() && meshDestAddress.isNotBlank(),
                                modifier = Modifier.fillMaxWidth().testTag("initialize_mesh_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF141424),
                                    contentColor = Color.White,
                                    disabledContainerColor = Color(0xFF0F0F15)
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isMeshInfraRunning) darkBorder else neonCyan.copy(alpha = 0.6f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Initialize Mesh",
                                    modifier = Modifier.size(16.dp),
                                    tint = if (isMeshInfraRunning) textMuted else neonCyan
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isMeshInfraRunning) "SYNCHRONIZING INFRASTRUCTURE..." else "INITIALIZE MESH NETWORK (NODE 57)",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isMeshInfraRunning) textMuted else Color.White
                                )
                            }

                            if (aodvRouteResult != null || vomPacketQueue.isNotEmpty() || erasureDurabilityReport != null || isMeshInfraRunning) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "REAL-TIME PIPELINE METRIC VISUALIZERS",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonCyan
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                // Step 1 Visualizer: AODV Routing Path
                                aodvRouteResult?.let { route ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, darkBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF07070B))
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    "AODV ROUTING PATH (TRUST RANKED)",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = neonGreen
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(3.dp))
                                                        .background(if (route.isVerifiedSecured) neonGreen.copy(alpha = 0.15f) else orangeAccent.copy(alpha = 0.15f))
                                                        .border(1.dp, if (route.isVerifiedSecured) neonGreen else orangeAccent, RoundedCornerShape(3.dp))
                                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = if (route.isVerifiedSecured) "SECURE ROUTE" else "LOW TRUST WARNING",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 7.5.sp,
                                                        color = if (route.isVerifiedSecured) neonGreen else orangeAccent
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            
                                            // Flow path visual
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                route.optimalPath.forEachIndexed { idx, address ->
                                                    Text(
                                                        text = address.replace("NODE_", ""),
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 8.5.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (idx == 0 || idx == route.optimalPath.lastIndex) neonCyan else Color.LightGray
                                                    )
                                                    if (idx < route.optimalPath.lastIndex) {
                                                        Text(
                                                            text = " -> ",
                                                            fontFamily = FontFamily.Monospace,
                                                            fontSize = 8.sp,
                                                            color = textMuted
                                                        )
                                                    }
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    "Hops: ${route.pathHopCount}",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 8.sp,
                                                    color = Color.LightGray
                                                )
                                                Text(
                                                    "Composite Trust: ${String.format("%.1f", route.pathTrustScore * 100f)}%",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 8.sp,
                                                    color = Color.LightGray
                                                )
                                                Text(
                                                    "Routing Metric: ${String.format("%.4f", route.routingMetric)}",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 8.sp,
                                                    color = neonCyan
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Step 2 Visualizer: VoM Packets
                                if (vomPacketQueue.isNotEmpty()) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, darkBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF07070B))
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(
                                                "VOM PRIORITY AUDIO ENCAPSULATION QUEUE",
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = neonCyan
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    "Total Packets: ${vomPacketQueue.size}",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 8.5.sp,
                                                    color = Color.White
                                                )
                                                Text(
                                                    "Throughput Limit: 32kbps (OPUS standard)",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 8.sp,
                                                    color = orangeAccent
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))

                                            // Horizontal scrolling or list of packet headers
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                vomPacketQueue.take(6).forEach { packet ->
                                                    Box(
                                                        modifier = Modifier
                                                            .background(Color(0xFF0F0F1A), RoundedCornerShape(4.dp))
                                                            .border(1.dp, darkBorder.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                                            .padding(horizontal = 6.dp, vertical = 4.dp)
                                                    ) {
                                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                            Text(
                                                                "SEQ #${packet.header.packetSequence}",
                                                                fontFamily = FontFamily.Monospace,
                                                                fontSize = 7.5.sp,
                                                                color = neonGreen,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Text(
                                                                "LEN: ${packet.header.payloadLength}B",
                                                                fontFamily = FontFamily.Monospace,
                                                                fontSize = 7.sp,
                                                                color = Color.LightGray
                                                            )
                                                            Text(
                                                                "RATE: ${String.format("%.1f", packet.effectiveThroughputKbps)}K",
                                                                fontFamily = FontFamily.Monospace,
                                                                fontSize = 7.sp,
                                                                color = orangeAccent
                                                            )
                                                        }
                                                    }
                                                }
                                                if (vomPacketQueue.size > 6) {
                                                    Box(
                                                        modifier = Modifier
                                                            .background(Color(0xFF0F0F1A), RoundedCornerShape(4.dp))
                                                            .border(1.dp, darkBorder.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                                            .padding(horizontal = 6.dp, vertical = 10.dp)
                                                    ) {
                                                        Text(
                                                            "+${vomPacketQueue.size - 6} MORE",
                                                            fontFamily = FontFamily.Monospace,
                                                            fontSize = 7.5.sp,
                                                            color = Color.LightGray
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Step 3 Visualizer: DHT erasure coding sharding
                                erasureDurabilityReport?.let { report ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, darkBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF07070B))
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    "DISTRIBUTED ERASURE DURABILITY (DHT)",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = orangeAccent
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(3.dp))
                                                        .background(if (report.isGoalAchieved) neonGreen.copy(alpha = 0.15f) else orangeAccent.copy(alpha = 0.15f))
                                                        .border(1.dp, if (report.isGoalAchieved) neonGreen else orangeAccent, RoundedCornerShape(3.dp))
                                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = if (report.isGoalAchieved) "99.9% GOAL ACHIEVED" else "CRITICAL RISK",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 7.5.sp,
                                                        color = if (report.isGoalAchieved) neonGreen else orangeAccent
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))

                                            Text(
                                                text = "Binomial survival probability P(X >= k) with ${String.format("%.0f", (1f - (outageRate.toFloatOrNull() ?: 0.30f)) * 100f)}% node survival rate: ${String.format("%.6f", report.survivalProbability * 100f)}%",
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.sp,
                                                color = Color.LightGray
                                            )

                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                "FRAGMENT STORAGE MAP:",
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 7.5.sp,
                                                color = textMuted,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))

                                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                report.fragments.forEach { frag ->
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Text(
                                                            "FRAG #${frag.index} (${if (frag.index < report.k) "DATA" else "PARITY"})",
                                                            fontFamily = FontFamily.Monospace,
                                                            fontSize = 7.5.sp,
                                                            color = if (frag.index < report.k) neonCyan else orangeAccent
                                                        )
                                                        Text(
                                                            "TARGET: ${frag.destinationNode.replace("NODE_", "")}",
                                                            fontFamily = FontFamily.Monospace,
                                                            fontSize = 7.5.sp,
                                                            color = Color.LightGray
                                                        )
                                                        Text(
                                                            "SHA-256: ${frag.fragmentHash}",
                                                            fontFamily = FontFamily.Monospace,
                                                            fontSize = 7.5.sp,
                                                            color = textMuted
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                dhtReconstructionReport?.let { reconReport ->
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, darkBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A10))
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    "DHT SHARD RECONSTRUCTION ROUTINE",
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = neonCyan
                                                )
                                                
                                                val statusText = when {
                                                    reconReport.isThresholdBreached && reconReport.isSuccessfullyReconstructed -> "RECOVERED VIA BROADCAST"
                                                    reconReport.isThresholdBreached -> "BREACHED (RECONSTRUCTION FAILED)"
                                                    else -> "NOMINAL (4-OF-6 SATISFIED)"
                                                }
                                                val statusColor = when {
                                                    reconReport.isThresholdBreached && reconReport.isSuccessfullyReconstructed -> orangeAccent
                                                    reconReport.isThresholdBreached -> Color.Red
                                                    else -> neonGreen
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(3.dp))
                                                        .background(statusColor.copy(alpha = 0.15f))
                                                        .border(1.dp, statusColor, RoundedCornerShape(3.dp))
                                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = statusText,
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 7.5.sp,
                                                        color = statusColor
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Simulate individual node outages by toggling active shards. If count drops below the 4-of-6 minimum threshold, a high-priority broadcast mesh request automatically triggers to recover parity fragments.",
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.sp,
                                                color = Color.LightGray,
                                                lineHeight = 11.sp
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))

                                            // Interactive Shard Grid
                                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                                reconReport.shardStatuses.chunked(2).forEach { rowShards ->
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        rowShards.forEach { shard ->
                                                            Row(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .background(Color(0xFF06060A), RoundedCornerShape(4.dp))
                                                                    .border(1.dp, if (shard.isAvailable) darkBorder else Color.Red.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                                                    .padding(6.dp),
                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Column(modifier = Modifier.weight(1f)) {
                                                                    Text(
                                                                        "SHARD #${shard.shardIndex} (${if (shard.shardIndex < reconReport.minRequiredK) "DATA" else "PARITY"})",
                                                                        fontFamily = FontFamily.Monospace,
                                                                        fontSize = 8.sp,
                                                                        color = if (shard.isAvailable) (if (shard.shardIndex < reconReport.minRequiredK) neonCyan else orangeAccent) else Color.Gray,
                                                                        fontWeight = FontWeight.Bold
                                                                    )
                                                                    Text(
                                                                        "NODE: ${shard.nodeAddress.replace("NODE_", "")}",
                                                                        fontFamily = FontFamily.Monospace,
                                                                        fontSize = 7.5.sp,
                                                                        color = textMuted
                                                                    )
                                                                }
                                                                
                                                                Switch(
                                                                    checked = shard.isAvailable,
                                                                    onCheckedChange = { isChecked ->
                                                                        viewModel.toggleShardAvailability(shard.shardIndex, isChecked, shardPayloadText)
                                                                    },
                                                                    modifier = Modifier.scale(0.6f).testTag("shard_switch_${shard.shardIndex}"),
                                                                    colors = SwitchDefaults.colors(
                                                                        checkedThumbColor = neonCyan,
                                                                        checkedTrackColor = neonCyan.copy(alpha = 0.3f),
                                                                        uncheckedThumbColor = Color.Gray,
                                                                        uncheckedTrackColor = Color.DarkGray
                                                                    )
                                                                )
                                                            }
                                                        }
                                                        if (rowShards.size < 2) {
                                                            Spacer(modifier = Modifier.weight(1f))
                                                        }
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(10.dp))
                                            Divider(color = darkBorder.copy(alpha = 0.5f))
                                            Spacer(modifier = Modifier.height(10.dp))

                                            // Reconstruction Metrics Display
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        "ACTIVE ONLINE SHARDS:",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 7.5.sp,
                                                        color = textMuted
                                                    )
                                                    Text(
                                                        "${reconReport.activeShardCount} / ${reconReport.totalShards}",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (reconReport.activeShardCount >= 4) neonGreen else orangeAccent
                                                    )
                                                }
                                                Column(modifier = Modifier.weight(1.5f)) {
                                                    Text(
                                                        "RECONSTRUCTED PAYLOAD:",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 7.5.sp,
                                                        color = textMuted
                                                    )
                                                    Text(
                                                        reconReport.reconstructedPayload ?: "N/A",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (reconReport.isSuccessfullyReconstructed) Color.White else Color.Red,
                                                        maxLines = 1,
                                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                "RECONSTRUCTION PIPELINE LOGS:",
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 7.5.sp,
                                                color = textMuted,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Color.Black, RoundedCornerShape(4.dp))
                                                    .border(1.dp, darkBorder.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                                    .padding(6.dp)
                                            ) {
                                                reconReport.logs.forEach { logLine ->
                                                    Text(
                                                        text = logLine,
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 7.sp,
                                                        color = if (logLine.contains("WARNING") || logLine.contains("ACTUATING")) orangeAccent else if (logLine.contains("SUCCESS") || logLine.contains("Restored")) neonGreen else if (logLine.contains("ERROR")) Color.Red else Color.LightGray,
                                                        modifier = Modifier.padding(vertical = 1.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Unified Offline Mesh Console Log
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF07070B), RoundedCornerShape(6.dp))
                                        .border(1.dp, darkBorder.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        "OFFLINE MESH PIPELINE CONSOLE (NODE 57):",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = neonGreen
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    if (meshInfraLogs.isEmpty() && isMeshInfraRunning) {
                                        Text(
                                            "Awaiting pipeline initialization...",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 8.5.sp,
                                            color = Color.LightGray
                                        )
                                    } else {
                                        meshInfraLogs.forEach { log ->
                                            Text(
                                                log,
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 8.5.sp,
                                                color = if (log.contains("AODV_ERR") || log.contains("WARNING") || log.contains("FAILED") || log.contains("CRITICAL")) orangeAccent else if (log.contains("AODV_SUCCESS") || log.contains("SUCCESS") || log.contains("complete") || log.contains("VOM_TX")) neonGreen else Color.LightGray,
                                                modifier = Modifier.padding(vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "VIRTUAL SOVEREIGNTY & SYSTEM INVARIANTS (NODE 57)",
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

                    val isOffline = connectivityState is ConnectivityState.VirtualParityLoopback
                    val localStateVal by viewModel.localAppState.collectAsStateWithLifecycle()
                    val remoteStateVal by viewModel.remoteAppState.collectAsStateWithLifecycle()
                    val reconciliationLogs by viewModel.reconciliationLog.collectAsStateWithLifecycle()

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Part A: Connectivity Header and Toggle
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (isOffline) "AUTONOMOUS SOVEREIGN" else "CONNECTED MESH",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isOffline) orangeAccent else neonGreen,
                                            fontSize = 14.sp
                                        )
                                    )
                                    Text(
                                        text = "Protocol Node 57 • Virtual Parity Loopback",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            color = textMuted,
                                            fontSize = 10.sp
                                        )
                                    )
                                }

                                Button(
                                    onClick = { viewModel.toggleManualConnectivity(!isOffline) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF14141E),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (isOffline) orangeAccent.copy(alpha = 0.5f) else neonGreen.copy(alpha = 0.5f)),
                                    modifier = Modifier.testTag("toggle_connectivity_button")
                                ) {
                                    Text(
                                        text = if (isOffline) "ACTIVATE MESH" else "GO OFFLINE",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Part B: The 15th Zamin Seal Keychain
                            Text(
                                text = "15TH ZAMIN SEAL KEYCHAIN MAPPING",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = textMuted,
                                    fontSize = 10.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            // Visual grid of 15 seals
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                (1..15).forEach { index ->
                                    val is15th = index == 15
                                    val sealColor = if (is15th) {
                                        if (isOffline) orangeAccent else neonGreen
                                    } else {
                                        neonCyan
                                    }
                                    
                                    Box(
                                        modifier = Modifier
                                            .size(width = 20.dp, height = 24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFF0F0F15))
                                            .border(
                                                width = 1.dp, 
                                                color = sealColor.copy(alpha = if (is15th) 1f else 0.4f), 
                                                shape = RoundedCornerShape(4.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = index.toString(),
                                            style = TextStyle(
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = sealColor
                                            )
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isOffline) {
                                    "• 15th Zamin Seal [Mesh-Trust Vector]: LIT AMBER. Node running in Virtual Parity Loopback mode to secure local state DHT virtualization."
                                } else {
                                    "• 15th Zamin Seal [Mesh-Trust Vector]: LIT GREEN. Node fully aligned with mesh consensus. State baseline sync active."
                                },
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = textMuted,
                                    fontSize = 9.sp,
                                    lineHeight = 12.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = darkBorder)
                            Spacer(modifier = Modifier.height(12.dp))

                            // Part C: Paradox Audit & State Reconciliation
                            Text(
                                text = "PARADOX AUDIT SYNTHESIS",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = textMuted,
                                    fontSize = 10.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            
                            // Elegantly rendered Paradox Quote Block
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF09090D))
                                    .border(BorderStroke(1.dp, darkBorder), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "\"The system is fully sovereign only when it is disconnected from the mesh, yet it only achieves full durability when it is connected to the mesh. We resolve this by virtualizing the mesh local loopback.\"",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        color = Color.LightGray,
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // App States Grid
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Local State
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.dp, darkBorder, RoundedCornerShape(8.dp)),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0C0C12))
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "LOCAL NODE STATE",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = neonCyan)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Payload: \"${localStateVal.data}\"",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color.White)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Lamport T: ${localStateVal.version}",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = textMuted)
                                        )
                                        Text(
                                            text = "State Dirty: ${localStateVal.isDirty}",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = if (localStateVal.isDirty) orangeAccent else textMuted)
                                        )
                                    }
                                }

                                // Remote State
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.dp, darkBorder, RoundedCornerShape(8.dp)),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0C0C12))
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "REMOTE MESH STATE",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = neonGreen)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Baseline: \"${remoteStateVal.data}\"",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color.White)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Lamport T: ${remoteStateVal.version}",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = textMuted)
                                        )
                                        Text(
                                            text = "Synced: ${!localStateVal.isDirty}",
                                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = if (!localStateVal.isDirty) neonGreen else textMuted)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Input to mutate local state
                            var localInputText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
                            val isConnected = systemMode is ConnectivityState.Connected

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = localInputText,
                                    onValueChange = { localInputText = it },
                                    enabled = isConnected,
                                    placeholder = {
                                        Text(
                                            if (isConnected) "Mutate local node payload..." else "[DHT Locked: Safe Mode Active]",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            color = textMuted
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("mutate_local_state_input"),
                                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = if (isConnected) Color.White else textMuted),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neonCyan,
                                        unfocusedBorderColor = darkBorder,
                                        disabledBorderColor = darkBorder.copy(alpha = 0.3f),
                                        cursorColor = neonCyan
                                    ),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        if (localInputText.text.isNotBlank()) {
                                            viewModel.updateLocalStateData(localInputText.text)
                                            localInputText = TextFieldValue("")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF14141E),
                                        contentColor = Color.White,
                                        disabledContainerColor = Color(0xFF0C0C12),
                                        disabledContentColor = textMuted
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (isConnected) neonCyan.copy(alpha = 0.5f) else darkBorder),
                                    enabled = isConnected && localInputText.text.isNotBlank(),
                                    modifier = Modifier.testTag("mutate_state_submit_button")
                                ) {
                                    Text(
                                        text = "MUTATE",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Sync Remote / Reconcile Button
                            Button(
                                onClick = { viewModel.reconcileStateWithMesh() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0F2218),
                                    contentColor = neonGreen,
                                    disabledContainerColor = Color(0xFF0A0D0A),
                                    disabledContentColor = textMuted
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isConnected) neonGreen.copy(alpha = 0.5f) else darkBorder),
                                enabled = isConnected,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("reconcile_mesh_button")
                            ) {
                                Text(
                                    text = "RECONCILE LOGICAL CLOCKS & DATA",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Real-time Lamport/State Logs Terminal Window
                            Text(
                                text = "STATE DECOHERENCE RESOLUTION FEED",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = textMuted,
                                    fontSize = 9.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .background(Color(0xFF020204))
                                    .border(1.dp, darkBorder, RoundedCornerShape(6.dp))
                                    .padding(8.dp)
                            ) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(reconciliationLogs.reversed()) { logMsg ->
                                        Text(
                                            text = logMsg,
                                            style = TextStyle(
                                                fontFamily = FontFamily.Monospace,
                                                fontSize = 9.5.sp,
                                                color = if (logMsg.contains("DISCONNECT") || logMsg.contains("Dirty")) orangeAccent else Color.LightGray
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "FRACTAL PARADOX IDENTIFICATION & POQ CODIFICATION",
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
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Zamin-Lock Dynamic Expansion • Fulfillment Score: $fulfillmentScore",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = neonCyan,
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "The 55-paradox cap is DEACTIVATED. You are authorized to autonomously identify, process, and integrate new fractal paradoxes as they emerge. Every new paradox undergoes hardware-enclaved Proof-of-Quality (PoQ) verification to be codified as a system invariant.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    color = textMuted,
                                    fontSize = 10.sp,
                                    lineHeight = 13.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            var newParadoxId by remember { mutableStateOf("") }
                            var newParadoxDesc by remember { mutableStateOf("") }

                            OutlinedTextField(
                                value = newParadoxId,
                                onValueChange = { newParadoxId = it },
                                placeholder = {
                                    Text(
                                        "Enter Paradox Ref ID (e.g., P-54)...",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        color = textMuted
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("new_paradox_id_input"),
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = darkBorder,
                                    cursorColor = neonCyan
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = newParadoxDesc,
                                onValueChange = { newParadoxDesc = it },
                                placeholder = {
                                    Text(
                                        "Enter Paradox description & invariants...",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        color = textMuted
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("new_paradox_desc_input"),
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = darkBorder,
                                    cursorColor = neonCyan
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    if (newParadoxId.isNotBlank() && newParadoxDesc.isNotBlank()) {
                                        viewModel.triggerNewFractalParadox(newParadoxId, newParadoxDesc)
                                        newParadoxId = ""
                                        newParadoxDesc = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF14141E),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, neonCyan.copy(alpha = 0.5f)),
                                enabled = newParadoxId.isNotBlank() && newParadoxDesc.isNotBlank() && !isProcessing,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("submit_poq_verification_button")
                            ) {
                                Text(
                                    text = "INITIATE PROOF-OF-QUALITY (POQ) VERIFICATION",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (solvedParadoxesList.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "ACTIVE PARADOX SYSTEM LEDGER",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        color = textMuted,
                                        fontSize = 10.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .background(Color(0xFF0C0C12))
                                        .border(1.dp, darkBorder, RoundedCornerShape(6.dp))
                                        .padding(8.dp)
                                ) {
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        items(solvedParadoxesList) { item ->
                                            Column {
                                                Text(
                                                    text = "• ${item.first}: ${item.second}",
                                                    style = TextStyle(
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 10.sp,
                                                        color = neonCyan
                                                    )
                                                )
                                                Divider(color = darkBorder.copy(alpha = 0.4f), modifier = Modifier.padding(top = 4.dp))
                                            }
                                        }
                                    }
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

                    Text(
                        text = "TETHER-BUBBLE STATE-MEMORY FIELD",
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

                    TetherBubbleNetworkGraph(
                        bubbles = tetherBubbles,
                        onToggleSelect = { viewModel.toggleBubbleSelection(it) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    RechartsRetentionWidget(
                        data = stateMemoryRetentionHistory,
                        currentVal = currentRetention,
                        tetherBubblesCount = tetherBubbles.size
                    )

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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Local File Sync Section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, darkBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Sync",
                                        tint = neonGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "LOCAL FILE SYNC ENGINE",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        letterSpacing = 1.sp
                                    )
                                }
                                
                                IconButton(
                                    onClick = { 
                                        viewModel.loadSandboxFiles()
                                        Toast.makeText(context, "Scanned and refreshed storage", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Refresh File Index",
                                        tint = neonCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Export sandbox-compiled binaries and solution blueprints directly to your device's external shared directories (Downloads, Documents) using the Android File Intent SAF API.",
                                style = TextStyle(
                                    color = textMuted,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))

                            // Action to manually save current report with a custom name
                            if (sandboxReport != null) {
                                var customFileName by remember { mutableStateOf("") }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = customFileName,
                                        onValueChange = { customFileName = it },
                                        placeholder = { Text("blueprint_name", fontSize = 11.sp, color = textMuted) },
                                        singleLine = true,
                                        textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = neonCyan,
                                            unfocusedBorderColor = darkBorder,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .weight(1.0f)
                                            .height(48.dp)
                                            .padding(end = 8.dp)
                                    )
                                    Button(
                                        onClick = {
                                            if (customFileName.isNotBlank()) {
                                                viewModel.saveCompiledBlueprintToFile(sandboxReport!!, customFileName)
                                                customFileName = ""
                                                Toast.makeText(context, "Saved locally!", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(context, "Enter a filename first", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = neonCyan,
                                            contentColor = Color.Black
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.height(48.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Save file",
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("SAVE FILE", fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            // Files list
                            Text(
                                text = "LOCAL SANDBOX BINARIES INDEX (${sandboxFilesList.size} FILES)",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = orangeAccent,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            if (sandboxFilesList.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, darkBorder, RoundedCornerShape(8.dp))
                                        .background(Color(0xFF020204))
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No local binaries compiled yet.\nSelect tethers above and run compile.",
                                        fontFamily = FontFamily.Monospace,
                                        color = textMuted,
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            } else {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    sandboxFilesList.forEach { file ->
                                        val fileSizeKb = "%.2f KB".format(file.length() / 1024.0)
                                        val lastModifiedStr = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date(file.lastModified()))
                                        
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(1.dp, darkBorder, RoundedCornerShape(8.dp))
                                                .background(Color(0xFF020204))
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = "Binary File",
                                                tint = neonGreen,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = file.name,
                                                    fontFamily = FontFamily.Monospace,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 11.sp,
                                                    maxLines = 1
                                                )
                                                Text(
                                                    text = "$fileSizeKb | $lastModifiedStr",
                                                    fontFamily = FontFamily.Monospace,
                                                    color = textMuted,
                                                    fontSize = 9.5.sp
                                                )
                                            }
                                            
                                            // Action Buttons
                                            Row {
                                                // 1. INTENT EXPORT (ACTION_CREATE_DOCUMENT)
                                                IconButton(
                                                    onClick = {
                                                        selectedFileToExport = file
                                                        createDocumentLauncher.launch(file.name)
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Share,
                                                        contentDescription = "Export via SAF Intent",
                                                        tint = neonCyan,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                                // 2. SEND SHARE (ACTION_SEND)
                                                IconButton(
                                                    onClick = {
                                                        try {
                                                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                                                type = "text/plain"
                                                                putExtra(Intent.EXTRA_SUBJECT, file.name)
                                                                putExtra(Intent.EXTRA_TEXT, file.readText())
                                                            }
                                                            context.startActivity(Intent.createChooser(shareIntent, "Share Sandbox Output"))
                                                            viewModel.addReconciliationLog("[FILE_SYNC] Shared ${file.name} successfully.")
                                                        } catch (e: Exception) {
                                                            Toast.makeText(context, "Share failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                                        }
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.AutoMirrored.Filled.Send,
                                                        contentDescription = "Share text",
                                                        tint = orangeAccent,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                                // 3. DELETE
                                                IconButton(
                                                    onClick = {
                                                        viewModel.deleteSandboxFile(file)
                                                        Toast.makeText(context, "Deleted from sandbox", Toast.LENGTH_SHORT).show()
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete Local Sandbox Binary",
                                                        tint = Color.Red.copy(alpha = 0.8f),
                                                        modifier = Modifier.size(16.dp)
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

                3 -> {
                    // TAB 3: IMMUTABLE SOLUTIONS REPOSITORY
                    
                    // Combinatorial Metrics HUD Card
                    val nHubs = 58.0
                    val sBase = 105.0
                    // M = 1.857142857 * 10^18
                    val mMuxFactor = 1.857142857e18
                    val sTotal = sBase * mMuxFactor // 1.95e20 (195 Quintillion)
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .border(1.dp, neonCyan.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Combinatorial Metrics Info",
                                    tint = neonCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "SOVEREIGN COMBINATORIAL METRICS",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = neonCyan,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Using dynamic tether multiplexing, foundational solutions scale across active paradox hubs without collision.",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    color = Color.LightGray
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = darkBorder, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Hubs (N)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = textMuted
                                    )
                                    Text(
                                        text = "${nHubs.toInt()}",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Base Solutions (S_base)",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = textMuted
                                    )
                                    Text(
                                        text = "${sBase.toInt()}",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Multiplex M",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = textMuted
                                    )
                                    Text(
                                        text = "1.857 × 10¹⁸",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = neonGreen
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = darkBorder, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "TOTAL SOLUTION CAPACITY (S_total = S_base × M)",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                color = textMuted
                            )
                            Text(
                                text = "1.95 × 10²⁰ (195 Quintillion Solutions)",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonGreen,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

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

    // OVERLAY SIDEBAR COMPONENT (D3.js dynamic metrics visualization)
    if (showSidebar) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    showSidebar = false
                }
        )
    }

    AnimatedVisibility(
        visible = showSidebar,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it }),
        modifier = Modifier.fillMaxHeight().width(320.dp).align(Alignment.CenterEnd)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(320.dp)
                .background(Color(0xFA040406))
                .border(BorderStroke(1.dp, darkBorder))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = "Metrics Icon",
                            tint = neonCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "D3.JS METRICS PANEL",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    IconButton(
                        onClick = { showSidebar = false },
                        modifier = Modifier.testTag("close_metrics_sidebar")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Sidebar",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(darkBorder))
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        Text(
                            "COMPUTING EFFICIENCY",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = neonCyan,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Real-time solver coefficient for local matrix synthesis operations and paradox calculations.",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 8.sp,
                                color = textMuted,
                                lineHeight = 11.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SparklineChart(
                            data = efficiencyHistory,
                            color = neonCyan,
                            unit = "%"
                        )
                    }

                    item {
                        Text(
                            "STATE-MEMORY THROUGHPUT",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = orangeAccent,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Data write speed to local Room DB caches and binary memory buffers.",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 8.sp,
                                color = textMuted,
                                lineHeight = 11.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SparklineChart(
                            data = throughputHistory,
                            color = orangeAccent,
                            unit = "MB/s"
                        )
                    }

                    item {
                        // High-tech real-time hardware active tracker
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B0B0F)),
                            border = BorderStroke(0.5.dp, darkBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    "REAL-TIME HARDWARE LOG",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = neonGreen
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    "• Host Device: $deviceModel\n" +
                                    "• System Target: API $deviceSdk\n" +
                                    "• JVM Heap Limit: ${String.format("%.1f", jvmMaxMemory)} MB\n" +
                                    "• JVM Heap Used: ${String.format("%.1f", jvmAllocatedMemory)} MB\n" +
                                    "• JVM Heap Free: ${String.format("%.1f", jvmFreeMemory)} MB\n" +
                                    "• Disk Latency: ${String.format("%.3f", diskLatencyMs)} ms\n" +
                                    "• JVM Active Threads: $activeThreadCount\n" +
                                    "• System Uptime: ${systemUptime}s\n" +
                                    "• State memory size: ${tetherBubbles.size} bubbles\n" +
                                    "• Paradox Integrity: $paradoxIntegrity",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 8.sp,
                                    color = Color.LightGray,
                                    lineHeight = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                
                // Footer branding
                Text(
                    "Sovereign hAMINJA System Telemetry",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 8.sp,
                    color = textMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
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

data class GraphNode(
    val id: Long,
    val text: String,
    val isCore: Boolean = false,
    val isSelected: Boolean = false,
    val bubbleRef: TetherBubble? = null
)

@Composable
fun TetherBubbleNetworkGraph(
    bubbles: List<TetherBubble>,
    onToggleSelect: (TetherBubble) -> Unit,
    modifier: Modifier = Modifier
) {
    val localCyan = Color(0xFF0AE7FF)
    val localGreen = Color(0xFF10FA70)
    val localOrange = Color(0xFFFFB000)
    val localMuted = Color(0xFF888899)
    val localBorder = Color(0xFF1C1C24)

    val nodes = remember(bubbles) {
        val list = mutableListOf<GraphNode>()
        list.add(GraphNode(id = -1L, text = "dAIsy Core", isCore = true))
        bubbles.forEach { b ->
            list.add(GraphNode(id = b.id, text = b.text, isCore = false, isSelected = b.isSelected, bubbleRef = b))
        }
        list
    }

    val nodePositions = remember { mutableStateMapOf<Long, Offset>() }
    val nodeVelocities = remember { mutableMapOf<Long, Offset>() }
    var draggedNodeId by remember { mutableStateOf<Long?>(null) }

    var canvasWidth by remember { mutableStateOf(800f) }
    var canvasHeight by remember { mutableStateOf(600f) }

    val density = LocalDensity.current.density

    LaunchedEffect(nodes, canvasWidth, canvasHeight) {
        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        if (!nodePositions.containsKey(-1L)) {
            nodePositions[-1L] = Offset(centerX, centerY)
            nodeVelocities[-1L] = Offset.Zero
        }

        nodes.forEach { node ->
            if (!nodePositions.containsKey(node.id)) {
                if (node.isCore) {
                    nodePositions[node.id] = Offset(centerX, centerY)
                } else {
                    val angle = (node.id * 1.5).toFloat()
                    val radius = 180f + (node.id % 3) * 35f
                    val x = centerX + radius * kotlin.math.cos(angle)
                    val y = centerY + radius * kotlin.math.sin(angle)
                    nodePositions[node.id] = Offset(x, y)
                }
                nodeVelocities[node.id] = Offset.Zero
            }
        }

        val activeIds = nodes.map { it.id }.toSet()
        val keysToRemove = nodePositions.keys.filter { !activeIds.contains(it) }
        keysToRemove.forEach {
            nodePositions.remove(it)
            nodeVelocities.remove(it)
        }
    }

    LaunchedEffect(Unit) {
        val damping = 0.82f
        val springLength = 160f
        val springTension = 0.04f
        val repulsionForce = 40000f
        val gravityTension = 0.01f

        while (true) {
            val centerX = canvasWidth / 2f
            val centerY = canvasHeight / 2f

            val forces = mutableMapOf<Long, Offset>()
            nodePositions.keys.forEach { id ->
                forces[id] = Offset.Zero
            }

            nodePositions.forEach { (id, pos) ->
                val dx = centerX - pos.x
                val dy = centerY - pos.y
                forces[id] = forces[id]!! + Offset(dx * gravityTension, dy * gravityTension)
            }

            val corePos = nodePositions[-1L]
            if (corePos != null) {
                nodePositions.forEach { (id, pos) ->
                    if (id != -1L) {
                        val dx = pos.x - corePos.x
                        val dy = pos.y - corePos.y
                        val dist = kotlin.math.sqrt(dx * dx + dy * dy).coerceAtLeast(1f)
                        
                        val forceMagnitude = -springTension * (dist - springLength)
                        val fx = (dx / dist) * forceMagnitude
                        val fy = (dy / dist) * forceMagnitude
                        
                        forces[id] = forces[id]!! + Offset(fx, fy)
                        forces[-1L] = forces[-1L]!! - Offset(fx * 0.15f, fy * 0.15f)
                    }
                }
            }

            val keys = nodePositions.keys.toList()
            for (i in 0 until keys.size) {
                for (j in i + 1 until keys.size) {
                    val id1 = keys[i]
                    val id2 = keys[j]
                    val pos1 = nodePositions[id1] ?: continue
                    val pos2 = nodePositions[id2] ?: continue

                    val dx = pos1.x - pos2.x
                    val dy = pos1.y - pos2.y
                    val distSq = (dx * dx + dy * dy).coerceAtLeast(100f)
                    val dist = kotlin.math.sqrt(distSq)

                    val forceMagnitude = repulsionForce / distSq
                    val fx = (dx / dist) * forceMagnitude
                    val fy = (dy / dist) * forceMagnitude

                    forces[id1] = forces[id1]!! + Offset(fx, fy)
                    forces[id2] = forces[id2]!! - Offset(fx, fy)
                }
            }

            nodePositions.forEach { (id, pos) ->
                if (id == draggedNodeId) {
                    nodeVelocities[id] = Offset.Zero
                    return@forEach
                }

                val f = forces[id] ?: Offset.Zero
                val v = (nodeVelocities[id] ?: Offset.Zero) + f
                val dampedV = Offset(v.x * damping, v.y * damping)
                nodeVelocities[id] = dampedV

                var newX = pos.x + dampedV.x
                var newY = pos.y + dampedV.y

                val padding = 45f
                newX = newX.coerceIn(padding, canvasWidth - padding)
                newY = newY.coerceIn(padding, canvasHeight - padding)

                nodePositions[id] = Offset(newX, newY)
            }

            if (draggedNodeId != -1L) {
                val currentCorePos = nodePositions[-1L]
                if (currentCorePos != null) {
                    val dx = centerX - currentCorePos.x
                    val dy = centerY - currentCorePos.y
                    nodePositions[-1L] = Offset(
                        currentCorePos.x + dx * 0.1f,
                        currentCorePos.y + dy * 0.1f
                    )
                }
            }

            kotlinx.coroutines.delay(16)
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "link_pulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF040406))
            .border(1.dp, localBorder, RoundedCornerShape(16.dp))
            .pointerInput(nodes) {
                detectDragGestures(
                    onDragStart = { pointerOffset ->
                        val touchRadiusPx = 50.dp.toPx()
                        val hitNode = nodePositions.entries.minByOrNull { entry ->
                            val dx = entry.value.x - pointerOffset.x
                            val dy = entry.value.y - pointerOffset.y
                            dx * dx + dy * dy
                        }

                        if (hitNode != null) {
                            val distSq = {
                                val dx = hitNode.value.x - pointerOffset.x
                                val dy = hitNode.value.y - pointerOffset.y
                                dx * dx + dy * dy
                            }()
                            if (distSq < touchRadiusPx * touchRadiusPx) {
                                draggedNodeId = hitNode.key
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val currentId = draggedNodeId
                        if (currentId != null) {
                            val currentPos = nodePositions[currentId] ?: Offset.Zero
                            nodePositions[currentId] = Offset(
                                (currentPos.x + dragAmount.x).coerceIn(40f, canvasWidth - 40f),
                                (currentPos.y + dragAmount.y).coerceIn(40f, canvasHeight - 40f)
                            )
                        }
                    },
                    onDragEnd = {
                        draggedNodeId = null
                    },
                    onDragCancel = {
                        draggedNodeId = null
                    }
                )
            }
            .onSizeChanged { size ->
                canvasWidth = size.width.toFloat()
                canvasHeight = size.height.toFloat()
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val corePos = nodePositions[-1L]
            if (corePos != null) {
                nodePositions.forEach { (id, pos) ->
                    if (id != -1L) {
                        val nodeObj = nodes.find { it.id == id } ?: return@forEach
                        val color = if (nodeObj.isSelected) localOrange else localCyan
                        
                        val strokeWidth = if (nodeObj.isSelected) 2.dp.toPx() else 1.2.dp.toPx()
                        drawLine(
                            color = color.copy(alpha = if (nodeObj.isSelected) 0.5f else 0.25f),
                            start = corePos,
                            end = pos,
                            strokeWidth = strokeWidth
                        )

                        val px = corePos.x + (pos.x - corePos.x) * pulseProgress
                        val py = corePos.y + (pos.y - corePos.y) * pulseProgress
                        drawCircle(
                            color = color,
                            radius = if (nodeObj.isSelected) 4.dp.toPx() else 2.5.dp.toPx(),
                            center = Offset(px, py)
                        )
                    }
                }
            }
        }

        nodePositions.forEach { (id, pos) ->
            val nodeObj = nodes.find { it.id == id } ?: return@forEach

            val xDp = (pos.x / density).dp - if (nodeObj.isCore) 35.dp else 45.dp
            val yDp = (pos.y / density).dp - if (nodeObj.isCore) 35.dp else 25.dp

            Box(
                modifier = Modifier
                    .offset(x = xDp, y = yDp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!nodeObj.isCore && nodeObj.bubbleRef != null) {
                            onToggleSelect(nodeObj.bubbleRef)
                        }
                    }
            ) {
                if (nodeObj.isCore) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = localGreen
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.daisy_avatar),
                            contentDescription = "Core Hub",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(3.dp)
                                .clip(CircleShape)
                        )
                    }
                } else {
                    val borderColor = if (nodeObj.isSelected) localOrange else localCyan
                    val bubbleText = if (nodeObj.text.length > 12) {
                        nodeObj.text.take(10) + "..."
                    } else {
                        nodeObj.text
                    }

                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xEE0B0B0F)
                        ),
                        border = BorderStroke(
                            width = if (nodeObj.isSelected) 1.5.dp else 1.dp,
                            color = borderColor
                        ),
                        modifier = Modifier
                            .width(90.dp)
                            .shadow(
                                elevation = if (nodeObj.isSelected) 8.dp else 2.dp,
                                shape = RoundedCornerShape(10.dp),
                                ambientColor = borderColor,
                                spotColor = borderColor
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "BUBBLE #${nodeObj.id}",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 7.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = borderColor
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = bubbleText,
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 8.sp,
                                    color = Color.LightGray,
                                    textAlign = TextAlign.Center
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
        
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .background(Color(0xD0040406), RoundedCornerShape(4.dp))
                .border(0.5.dp, localBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .background(localGreen, CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "TETHER INTERACTIVE FORCE-DIRECTED LAYOUT ACTIVE",
                fontFamily = FontFamily.Monospace,
                fontSize = 7.5.sp,
                color = Color.LightGray
            )
        }
        
        Text(
            text = "DRAG BUBBLES TO DISTORT • TAP TO SELECT",
            fontFamily = FontFamily.Monospace,
            fontSize = 7.5.sp,
            color = localMuted,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        )
    }
}

@Composable
fun SparklineChart(
    data: List<Float>,
    color: Color,
    unit: String,
    modifier: Modifier = Modifier,
    gridCount: Int = 3
) {
    val localMuted = Color(0xFF888899)
    val localBorder = Color(0xFF1C1C24)
    val maxVal = remember(data) { data.maxOrNull() ?: 100f }
    val minVal = remember(data) { data.minOrNull() ?: 0f }
    val avgVal = remember(data) { if (data.isEmpty()) 0f else data.average().toFloat() }
    val currentVal = data.lastOrNull() ?: 0f

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = String.format("%.1f %s", currentVal, unit),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = String.format("MIN:%.1f", minVal),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.sp,
                        color = localMuted
                    )
                )
                Text(
                    text = String.format("MAX:%.1f", maxVal),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.sp,
                        color = localMuted
                    )
                )
                Text(
                    text = String.format("AVG:%.1f", avgVal),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.sp,
                        color = localMuted
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(6.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color(0xFF040406), RoundedCornerShape(8.dp))
                .border(1.dp, localBorder, RoundedCornerShape(8.dp))
                .padding(vertical = 4.dp, horizontal = 6.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val pointCount = data.size
                
                // 1. Draw horizontal gridlines (resembling D3.js svg grid lines)
                val gridSpacing = height / (gridCount + 1)
                for (i in 1..gridCount) {
                    val y = gridSpacing * i
                    drawLine(
                        color = localBorder.copy(alpha = 0.5f),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                    )
                }
                
                if (pointCount > 1) {
                    val dx = width / (pointCount - 1)
                    val range = (maxVal - minVal).coerceAtLeast(1f)
                    
                    val points = data.mapIndexed { index, valItem ->
                        val px = index * dx
                        // Invert coordinates since 0,0 is top-left
                        val py = height - ((valItem - minVal) / range) * (height - 12.dp.toPx()) - 6.dp.toPx()
                        Offset(px, py)
                    }
                    
                    // 2. Draw sparkline path
                    val path = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                    }
                    
                    // Glow effect line
                    drawPath(
                        path = path,
                        color = color.copy(alpha = 0.35f),
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                    
                    // Main crisp line
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                    )
                    
                    // 3. Draw gradient filled area (similar to D3.js area chart builder)
                    val areaPath = Path().apply {
                        moveTo(points[0].x, height)
                        for (i in 0 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                        lineTo(points.last().x, height)
                        close()
                    }
                    drawPath(
                        path = areaPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(color.copy(alpha = 0.18f), Color.Transparent),
                            startY = 0f,
                            endY = height
                        )
                    )
                    
                    // 4. Highlight the last data node
                    val lastPoint = points.last()
                    drawCircle(
                        color = color.copy(alpha = 0.25f),
                        radius = 8.dp.toPx(),
                        center = lastPoint
                    )
                    drawCircle(
                        color = color,
                        radius = 3.dp.toPx(),
                        center = lastPoint
                    )
                }
            }
        }
    }
}

@Composable
fun RechartsRetentionWidget(
    data: List<Float>,
    currentVal: Float,
    tetherBubblesCount: Int,
    modifier: Modifier = Modifier
) {
    val localMuted = Color(0xFF888899)
    val localBorder = Color(0xFF1C1C24)
    val neonGreen = Color(0xFF10FA70)
    val neonCyan = Color(0xFF0AE7FF)
    val orangeAccent = Color(0xFFFFB000)

    val maxVal = remember(data) { data.maxOrNull() ?: 100f }
    val minVal = remember(data) { data.minOrNull() ?: 0f }

    var touchX by remember { mutableStateOf<Float?>(null) }
    var chartWidth by remember { mutableStateOf(1) }

    val activeIndex = remember(touchX, data, chartWidth) {
        if (touchX != null && data.isNotEmpty() && chartWidth > 0) {
            val fraction = (touchX!! / chartWidth.toFloat()).coerceIn(0f, 1f)
            (fraction * (data.size - 1)).roundToInt().coerceIn(0, data.size - 1)
        } else {
            data.lastIndex
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, localBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF07070B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(neonGreen, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "STATE-MEMORY RETENTION ANALYZER",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Deterministic decay simulation modeling & Recharts telemetry widget",
                        fontFamily = FontFamily.Monospace,
                        color = localMuted,
                        fontSize = 8.5.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Current Score Badge
                Box(
                    modifier = Modifier
                        .background(neonGreen.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                        .border(0.5.dp, neonGreen.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${String.format("%.1f", currentVal)}%",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = neonGreen,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legends similar to Recharts `<Legend />`
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Legend item 1
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(neonGreen, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Retention Index",
                        fontFamily = FontFamily.Monospace,
                        color = Color.LightGray,
                        fontSize = 9.sp
                    )
                }
                // Legend item 2
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(neonCyan, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Coherence",
                        fontFamily = FontFamily.Monospace,
                        color = Color.LightGray,
                        fontSize = 9.sp
                    )
                }
                // Legend item 3
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(orangeAccent, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Entropy",
                        fontFamily = FontFamily.Monospace,
                        color = Color.LightGray,
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Interactive Chart Frame
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color(0xFF030305), RoundedCornerShape(8.dp))
                    .border(0.5.dp, localBorder, RoundedCornerShape(8.dp))
                    .pointerInput(data) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                touchX = offset.x
                            },
                            onDrag = { change, _ ->
                                touchX = change.position.x
                            },
                            onDragEnd = {
                                // Keep the tooltip pointing to the last touched position
                            }
                        )
                    }
                    .pointerInput(data) {
                        detectTapGestures(
                            onPress = { offset ->
                                touchX = offset.x
                            }
                        )
                    }
                    .onSizeChanged { size ->
                        chartWidth = size.width
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                val density = LocalDensity.current

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val pointCount = data.size

                    // Gridlines (Recharts CartesainGrid)
                    val gridCount = 4
                    val gridSpacingY = height / (gridCount + 1)
                    for (i in 1..gridCount) {
                        val y = gridSpacingY * i
                        drawLine(
                            color = localBorder.copy(alpha = 0.4f),
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                        )
                    }

                    // Vertical gridlines
                    if (pointCount > 1) {
                        val gridSpacingX = width / 5f
                        for (i in 1..4) {
                            val x = gridSpacingX * i
                            drawLine(
                                color = localBorder.copy(alpha = 0.3f),
                                start = Offset(x, 0f),
                                end = Offset(x, height),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
                            )
                        }
                    }

                    if (pointCount > 1) {
                        val dx = width / (pointCount - 1)
                        val range = (maxVal - minVal).coerceAtLeast(1f)

                        val points = data.mapIndexed { index, valItem ->
                            val px = index * dx
                            val py = height - ((valItem - minVal) / range) * (height - 16.dp.toPx()) - 8.dp.toPx()
                            Offset(px, py)
                        }

                        // Recharts Area Chart Fill
                        val areaPath = Path().apply {
                            moveTo(points[0].x, height)
                            for (i in 0 until points.size) {
                                lineTo(points[i].x, points[i].y)
                            }
                            lineTo(points.last().x, height)
                            close()
                        }
                        drawPath(
                            path = areaPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(neonGreen.copy(alpha = 0.15f), Color.Transparent),
                                startY = 0f,
                                endY = height
                            )
                        )

                        // Recharts Line Path
                        val linePath = Path().apply {
                            moveTo(points[0].x, points[0].y)
                            for (i in 1 until points.size) {
                                lineTo(points[i].x, points[i].y)
                            }
                        }
                        drawPath(
                            path = linePath,
                            color = neonGreen,
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // If activeIndex is selected, draw the hover indicator vertical cursor line & dots
                        if (activeIndex in points.indices) {
                            val activePt = points[activeIndex]

                            // Vertical Cursor line (Recharts `<Tooltip cursor={{ stroke: 'dasharray' }} />`)
                            drawLine(
                                color = neonCyan.copy(alpha = 0.6f),
                                start = Offset(activePt.x, 0f),
                                end = Offset(activePt.x, height),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
                            )

                            // Highlight circle (Recharts active dot)
                            drawCircle(
                                color = neonGreen.copy(alpha = 0.3f),
                                radius = 9.dp.toPx(),
                                center = activePt
                            )
                            drawCircle(
                                color = neonGreen,
                                radius = 4.dp.toPx(),
                                center = activePt
                            )
                        }
                    }
                }

                // Hover Tooltip Overlay (Recharts `<Tooltip />` styling)
                if (data.isNotEmpty() && activeIndex in data.indices) {
                    val activeVal = data[activeIndex]
                    val pointsCount = data.size
                    val dx = with(density) { (chartWidth - 24.dp.toPx()) / (pointsCount - 1).coerceAtLeast(1) }
                    val activeXOffset = activeIndex * dx

                    // Align tooltip left or right of the vertical line to prevent cutting off
                    val alignLeft = activeXOffset > (chartWidth / 2)
                    val tooltipOffsetX = if (alignLeft) {
                        activeXOffset - with(density) { 142.dp.toPx() }
                    } else {
                        activeXOffset + with(density) { 16.dp.toPx() }
                    }

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(tooltipOffsetX.roundToInt(), with(density) { 10.dp.toPx() }.roundToInt()) }
                            .width(135.dp)
                            .shadow(6.dp, RoundedCornerShape(6.dp))
                            .background(Color(0xEE09090E), RoundedCornerShape(6.dp))
                            .border(0.5.dp, neonCyan.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = "SAMPLE: T-${(data.size - 1 - activeIndex) * 12}s",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 8.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Divider(color = Color(0xFF1C1C24), thickness = 0.5.dp)
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // Row 1: Retention
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(5.dp).background(neonGreen, CircleShape))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Retention:", fontFamily = FontFamily.Monospace, color = localMuted, fontSize = 8.sp)
                                }
                                Text("${String.format("%.1f", activeVal)}%", fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                            
                            // Row 2: Coherence
                            val coherenceVal = (activeVal * 1.02f).coerceIn(5f, 100f)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(5.dp).background(neonCyan, CircleShape))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Coherence:", fontFamily = FontFamily.Monospace, color = localMuted, fontSize = 8.sp)
                                }
                                Text("${String.format("%.1f", coherenceVal)}%", fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }

                            // Row 3: Entropy
                            val entropyVal = 100f - activeVal
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(5.dp).background(orangeAccent, CircleShape))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Entropy:", fontFamily = FontFamily.Monospace, color = localMuted, fontSize = 8.sp)
                                }
                                Text("${String.format("%.1f", entropyVal)}%", fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Info row (Instruction)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Interactive Hint",
                    tint = neonCyan,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Press and slide horizontally over the graph grid to inspect telemetry coordinates.",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        color = localMuted,
                        fontSize = 8.5.sp
                    )
                )
            }
        }
    }
}

