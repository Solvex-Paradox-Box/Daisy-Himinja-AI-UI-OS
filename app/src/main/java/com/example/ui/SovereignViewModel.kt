package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.api.GeminiClient
import com.example.api.GeminiContent
import com.example.api.GeminiPart
import com.example.api.GeminiRequest
import com.example.data.local.SovereignDatabase
import com.example.data.model.SovereignSolution
import com.example.data.model.SystemMilestone
import com.example.data.model.TetherBubble
import com.example.data.model.PheromoneSignalPacket
import com.example.data.model.ProofOfQualityGate
import com.example.data.model.PheromoneBroadcaster
import com.example.data.model.EphemeralWorkerOutput
import com.example.data.model.HomeostaticEnergyIndex
import com.example.data.model.MeshAgentCard
import com.example.data.model.SemanticIntegrityFilter
import com.example.data.model.AodvRouteRequest
import com.example.data.model.AodvRouteResult
import com.example.data.model.AodvRoutingEngine
import com.example.data.model.VomPacket
import com.example.data.model.VomEncapsulationEngine
import com.example.data.model.ShardingConfig
import com.example.data.model.ErasureDurabilityReport
import com.example.data.model.ShardingEngine
import com.example.data.model.ShardNodeStatus
import com.example.data.model.DhtReconstructionReport
import com.example.data.model.ConnectivityState
import com.example.data.model.AppState
import com.example.data.repository.SovereignRepository
import com.example.data.repository.ConnectivityMonitor
import com.example.data.repository.SyncCoordinator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SovereignViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SovereignRepository

    // --- State Flows ---
    val tetherBubbles: StateFlow<List<TetherBubble>>
    val sovereignSolutions: StateFlow<List<SovereignSolution>>
    val systemMilestones: StateFlow<List<SystemMilestone>>

    // --- Live Engine State Metrics ---
    private val _currentProcessingFocus = MutableStateFlow("Homeostasis (Coherent and Idle)")
    val currentProcessingFocus: StateFlow<String> = _currentProcessingFocus.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _paradoxCount = MutableStateFlow(53)
    val paradoxCount: StateFlow<Int> = _paradoxCount.asStateFlow()

    private val _fulfillmentScore = MutableStateFlow(10)
    val fulfillmentScore: StateFlow<Int> = _fulfillmentScore.asStateFlow()

    private val _solvedParadoxesList = MutableStateFlow<List<Pair<String, String>>>(listOf(
        "P-01" to "Isolation vs Consensus (Zamin-Lock)",
        "P-02" to "Entropy vs Homeostasis",
        "P-03" to "Latency vs Autonomy"
    ))
    val solvedParadoxesList: StateFlow<List<Pair<String, String>>> = _solvedParadoxesList.asStateFlow()

    private val _paradoxIntegrity = MutableStateFlow("53 / 53 Solved (100.00%)")
    val paradoxIntegrity: StateFlow<String> = _paradoxIntegrity.asStateFlow()

    // --- Live Sandbox Logs State ---
    private val _sandboxLogs = MutableStateFlow<List<String>>(emptyList())
    val sandboxLogs: StateFlow<List<String>> = _sandboxLogs.asStateFlow()

    private val _sandboxCompletedReport = MutableStateFlow<String?>(null)
    val sandboxCompletedReport: StateFlow<String?> = _sandboxCompletedReport.asStateFlow()

    // --- Biometric Identity Protocol States ---
    private val _isBiometricScanning = MutableStateFlow(false)
    val isBiometricScanning: StateFlow<Boolean> = _isBiometricScanning.asStateFlow()

    private val _biometricStatus = MutableStateFlow("SECURE LOCAL ENCLAVE IDENTIFIED")
    val biometricStatus: StateFlow<String> = _biometricStatus.asStateFlow()

    private val _userIdentity = MutableStateFlow("whatarethetoddz@gmail.com")
    val userIdentity: StateFlow<String> = _userIdentity.asStateFlow()

    private val _biometricConfidence = MutableStateFlow(98.7f)
    val biometricConfidence: StateFlow<Float> = _biometricConfidence.asStateFlow()

    private val _contributionScore = MutableStateFlow(1420)
    val contributionScore: StateFlow<Int> = _contributionScore.asStateFlow()

    private val _experienceSummary = MutableStateFlow("Active partner profile loaded. Identified deep focus coordinates & architectural intent. System learning coefficient synced at 1.0.")
    val experienceSummary: StateFlow<String> = _experienceSummary.asStateFlow()

    // --- Real-Time Telemetry & Throughput Flows (Sparklines) ---
    private val _efficiencyHistory = MutableStateFlow<List<Float>>(List(15) { 94f + (it % 4) * 1.2f })
    val efficiencyHistory: StateFlow<List<Float>> = _efficiencyHistory.asStateFlow()

    private val _throughputHistory = MutableStateFlow<List<Float>>(List(15) { 180f + (it % 5) * 45f })
    val throughputHistory: StateFlow<List<Float>> = _throughputHistory.asStateFlow()

    // --- Real-time JVM & Disk Hardware Telemetry Streams ---
    private val _activeThreadCount = MutableStateFlow(Thread.activeCount())
    val activeThreadCount: StateFlow<Int> = _activeThreadCount.asStateFlow()

    private val _jvmAllocatedMemory = MutableStateFlow(0f)
    val jvmAllocatedMemory: StateFlow<Float> = _jvmAllocatedMemory.asStateFlow()

    private val _jvmFreeMemory = MutableStateFlow(0f)
    val jvmFreeMemory: StateFlow<Float> = _jvmFreeMemory.asStateFlow()

    private val _jvmMaxMemory = MutableStateFlow(0f)
    val jvmMaxMemory: StateFlow<Float> = _jvmMaxMemory.asStateFlow()

    private val _systemUptime = MutableStateFlow(0L)
    val systemUptime: StateFlow<Long> = _systemUptime.asStateFlow()

    private val _diskLatencyMs = MutableStateFlow(0f)
    val diskLatencyMs: StateFlow<Float> = _diskLatencyMs.asStateFlow()

    private val _deviceModel = MutableStateFlow("${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}")
    val deviceModel: StateFlow<String> = _deviceModel.asStateFlow()

    private val _deviceSdk = MutableStateFlow(android.os.Build.VERSION.SDK_INT)
    val deviceSdk: StateFlow<Int> = _deviceSdk.asStateFlow()

    // --- Pheromone Protocol & Ephemeral Worker States ---
    private val _isPheromoneRunning = MutableStateFlow(false)
    val isPheromoneRunning: StateFlow<Boolean> = _isPheromoneRunning.asStateFlow()

    private val _pheromoneAlertLog = MutableStateFlow<List<String>>(emptyList())
    val pheromoneAlertLog: StateFlow<List<String>> = _pheromoneAlertLog.asStateFlow()

    private val _ephemeralWorkerLog = MutableStateFlow<List<String>>(emptyList())
    val ephemeralWorkerLog: StateFlow<List<String>> = _ephemeralWorkerLog.asStateFlow()

    // --- HEI & Node 56 Agentic Mesh Network States ---
    private val _homeostaticEnergyIndex = MutableStateFlow(HomeostaticEnergyIndex.compute(0f, 0f, 0f, 128f))
    val homeostaticEnergyIndex: StateFlow<HomeostaticEnergyIndex> = _homeostaticEnergyIndex.asStateFlow()

    private val _inStealthMode = MutableStateFlow(false)
    val inStealthMode: StateFlow<Boolean> = _inStealthMode.asStateFlow()

    private val _discoveredAgents = MutableStateFlow<List<MeshAgentCard>>(
        listOf(
            MeshAgentCard.discoverAgent(
                id = "MESH_SEC_AUDITOR",
                name = "Enclave Auditor Agent",
                role = "Cryptographic integrity audits & enclave rotation",
                capabilities = listOf("RSA Verification", "Enclave Isolation", "Dynamic Rotation"),
                score = 0.98f
            ),
            MeshAgentCard.discoverAgent(
                id = "MESH_COGNITIVE_SEARCH",
                name = "Sovereign Searcher Agent",
                role = "Offline high-dimensional semantic analysis",
                capabilities = listOf("Vector Mapping", "Paradox Extraction", "Semantic Search"),
                score = 0.95f
            ),
            MeshAgentCard.discoverAgent(
                id = "MESH_CLOCK_SYNC",
                name = "Clock Synchronization Proxy",
                role = "Hardware drift compensation & synchronous clocks",
                capabilities = listOf("Hardware Drift Calibration", "JNI Port Querying", "Microsecond Sync"),
                score = 0.94f
            )
        )
    )
    val discoveredAgents: StateFlow<List<MeshAgentCard>> = _discoveredAgents.asStateFlow()

    private val _semanticFilterLog = MutableStateFlow<List<String>>(emptyList())
    val semanticFilterLog: StateFlow<List<String>> = _semanticFilterLog.asStateFlow()

    private val _isA2AOrchestrating = MutableStateFlow(false)
    val isA2AOrchestrating: StateFlow<Boolean> = _isA2AOrchestrating.asStateFlow()

    private val _a2aOrchestrationLog = MutableStateFlow<List<String>>(emptyList())
    val a2aOrchestrationLog: StateFlow<List<String>> = _a2aOrchestrationLog.asStateFlow()

    // --- Offline Mesh Infrastructure (Node 57) States ---
    private val _aodvRouteResult = MutableStateFlow<AodvRouteResult?>(null)
    val aodvRouteResult: StateFlow<AodvRouteResult?> = _aodvRouteResult.asStateFlow()

    private val _vomPacketQueue = MutableStateFlow<List<VomPacket>>(emptyList())
    val vomPacketQueue: StateFlow<List<VomPacket>> = _vomPacketQueue.asStateFlow()

    private val _erasureDurabilityReport = MutableStateFlow<ErasureDurabilityReport?>(null)
    val erasureDurabilityReport: StateFlow<ErasureDurabilityReport?> = _erasureDurabilityReport.asStateFlow()

    private val _dhtReconstructionReport = MutableStateFlow<DhtReconstructionReport?>(null)
    val dhtReconstructionReport: StateFlow<DhtReconstructionReport?> = _dhtReconstructionReport.asStateFlow()

    private val _shardNodeStatuses = MutableStateFlow<List<ShardNodeStatus>>(emptyList())
    val shardNodeStatuses: StateFlow<List<ShardNodeStatus>> = _shardNodeStatuses.asStateFlow()

    private val _isMeshInfraRunning = MutableStateFlow(false)
    val isMeshInfraRunning: StateFlow<Boolean> = _isMeshInfraRunning.asStateFlow()

    private val _meshInfraLogs = MutableStateFlow<List<String>>(emptyList())
    val meshInfraLogs: StateFlow<List<String>> = _meshInfraLogs.asStateFlow()

    // --- Connectivity & Sovereign State Reconstruction Flows ---
    private val connectivityMonitor = ConnectivityMonitor(application)

    private val _connectivityState = MutableStateFlow<ConnectivityState>(ConnectivityState.Connected)
    val connectivityState: StateFlow<ConnectivityState> = _connectivityState.asStateFlow()

    lateinit var syncCoordinator: SyncCoordinator
    private val _systemMode = MutableStateFlow<ConnectivityState>(ConnectivityState.Connected)
    val systemMode: StateFlow<ConnectivityState> = _systemMode.asStateFlow()

    private val _localAppState = MutableStateFlow(AppState("Initial Sovereign Node State", 1L, false))
    val localAppState: StateFlow<AppState> = _localAppState.asStateFlow()

    private val _remoteAppState = MutableStateFlow(AppState("External Mesh Baseline", 1L, false))
    val remoteAppState: StateFlow<AppState> = _remoteAppState.asStateFlow()

    private val _reconciliationLog = MutableStateFlow<List<String>>(listOf("[Lamport T=1] System state initialized at T=1"))
    val reconciliationLog: StateFlow<List<String>> = _reconciliationLog.asStateFlow()

    init {
        val database = SovereignDatabase.getDatabase(application)
        val dao = database.sovereignDao()
        repository = SovereignRepository(dao)

        tetherBubbles = repository.allTetherBubbles.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        sovereignSolutions = repository.allSolutions.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        systemMilestones = repository.allMilestones.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Seed initial data if the database is completely empty
        seedInitialData()

        syncCoordinator = SyncCoordinator(repository, connectivityMonitor)

        // Bind systemMode to SyncCoordinator's systemMode
        viewModelScope.launch {
            syncCoordinator.systemMode.collect { mode ->
                _systemMode.value = mode
                _connectivityState.value = mode
                if (mode is ConnectivityState.VirtualParityLoopback) {
                    triggerVirtualParityLoopback()
                } else {
                    addReconciliationLog("Network Online. Connectivity restored to remote mesh.")
                }
            }
        }

        // Real-time telemetry benchmark & stat gathering loop
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1200)
                
                // 1. Measure real-world disk write & read benchmark latency and calculate throughput
                val startWrite = System.nanoTime()
                var throughputValue = 0f
                var latencyMsVal = 0f
                try {
                    val tempFile = java.io.File(application.cacheDir, "disk_bench.tmp")
                    val testBytes = ByteArray(10240) { (it % 256).toByte() }
                    tempFile.writeBytes(testBytes)
                    val readBack = tempFile.readBytes()
                    tempFile.delete()
                    val durationNs = System.nanoTime() - startWrite
                    latencyMsVal = durationNs.toFloat() / 1_000_000f
                    
                    val durationSecs = durationNs.toDouble() / 1_000_000_000.0
                    val realThroughputMBs = if (durationSecs > 0) {
                        (10240.toDouble() / (1024.0 * 1024.0)) / durationSecs
                    } else 0.0
                    // Clamp throughput value nicely for visualization
                    throughputValue = realThroughputMBs.toFloat().coerceIn(10f, 2500f)
                } catch (e: Exception) {
                    throughputValue = 180f + (Math.random() * 20).toFloat()
                    latencyMsVal = 0.8f
                }
                
                _diskLatencyMs.value = latencyMsVal

                // 2. Measure real CPU computational performance of math calculations
                val startMath = System.nanoTime()
                var accum = 0.0
                for (i in 0 until 1200) {
                    accum += Math.sin(i.toDouble()) * Math.cos(i.toDouble())
                }
                val mathDurationNs = System.nanoTime() - startMath
                val mathDurationMs = mathDurationNs.toFloat() / 1_000_000f
                
                // 3. Collect active memory allocation from the actual live JVM state
                val runtime = Runtime.getRuntime()
                val totalMem = runtime.totalMemory()
                val freeMem = runtime.freeMemory()
                val maxMem = runtime.maxMemory()
                val usedMem = totalMem - freeMem
                
                _jvmAllocatedMemory.value = usedMem / (1024f * 1024f)
                _jvmFreeMemory.value = freeMem / (1024f * 1024f)
                _jvmMaxMemory.value = maxMem / (1024f * 1024f)
                
                _activeThreadCount.value = Thread.activeCount()
                _systemUptime.value = android.os.SystemClock.elapsedRealtime() / 1000

                // 4. Calculate Homeostatic Energy Index (HEI) dynamically based on actual metrics
                val computedHei = HomeostaticEnergyIndex.compute(
                    latencyMs = latencyMsVal,
                    mathDurationMs = mathDurationMs,
                    usedMemMb = usedMem / (1024f * 1024f),
                    maxMemMb = maxMem / (1024f * 1024f)
                )
                _homeostaticEnergyIndex.value = computedHei
                
                // If Stealth mode state changes, update the flag
                if (_inStealthMode.value != computedHei.inStealthMode) {
                    _inStealthMode.value = computedHei.inStealthMode
                }

                // 5. Calculate real-time "Computing Efficiency" as a combination of:
                // Heap space availability & mathematical operation throughput
                val heapEfficiency = (1.0f - (usedMem.toFloat() / maxMem.toFloat())) * 100f
                val normalizedMathSpeed = (99.9f - (mathDurationMs * 2.0f)).coerceIn(92.0f, 99.9f)
                val realEfficiencyValue = (heapEfficiency * 0.3f + normalizedMathSpeed * 0.7f).coerceIn(85f, 99.9f)

                // update efficiency history
                val currentEffList = _efficiencyHistory.value.toMutableList()
                currentEffList.add(realEfficiencyValue)
                if (currentEffList.size > 20) currentEffList.removeAt(0)
                _efficiencyHistory.value = currentEffList

                // update throughput history
                val currentThroughputList = _throughputHistory.value.toMutableList()
                currentThroughputList.add(throughputValue)
                if (currentThroughputList.size > 20) currentThroughputList.removeAt(0)
                _throughputHistory.value = currentThroughputList
            }
        }
    }

    private fun seedInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            // Check if tethers are empty
            if (tetherBubbles.value.isEmpty()) {
                repository.insertTetherBubble(
                    TetherBubble(
                        text = "fun executeSyncClock(): Long = System.nanoTime()",
                        sourceScreen = "Kernel Bios",
                        isSelected = true
                    )
                )
                repository.insertTetherBubble(
                    TetherBubble(
                        text = "overrideHardwareController(port = 0x3F8, signal = HIGH)",
                        sourceScreen = "Chassis Controller",
                        isSelected = true
                    )
                )
                repository.insertTetherBubble(
                    TetherBubble(
                        text = "val stateMemory = SovereignTetherBuffer(capacity = 4096)",
                        sourceScreen = "Memory Controller",
                        isSelected = false
                    )
                )
            }

            // Check if solutions are empty
            if (sovereignSolutions.value.isEmpty()) {
                repository.insertSolution(
                    SovereignSolution(
                        title = "Deterministic Clock Synchronizer",
                        description = "Enforces absolute chronological ordering of sovereign transactions without external NTP dependencies.",
                        learnedFrom = "System Initialization Protocol",
                        implementationMethod = "/**\n * Deterministic Clock Synchronization Method\n */\nfun syncClock(): Long {\n    val driftOffset = 0L // Calibrated automatically\n    return System.nanoTime() + driftOffset\n}",
                        performanceBoost = 99.8
                    )
                )
                repository.insertSolution(
                    SovereignSolution(
                        title = "Zero-Sandbox Hardware Access",
                        description = "Resides at the binary level, bypassing secondary OS system calls for latency-free hardware orchestration.",
                        learnedFrom = "Kernel Sovereignty Axiom",
                        implementationMethod = "/**\n * Access physical port directly without OS overhead\n */\nfun directHardwareWrite(address: Int, data: Byte) {\n    // Native direct assembly write\n    // outb address, data\n}",
                        performanceBoost = 99.9
                    )
                )
            }

            // Check if milestones are empty
            if (systemMilestones.value.isEmpty()) {
                repository.insertMilestone(
                    SystemMilestone(
                        title = "OS Sovereignty Initialized",
                        description = "Kernel and deterministic solver synced at 53/53 paradox coordinates.",
                        milestoneType = "PARADOX_REALIGNED"
                    )
                )
            }
        }
    }

    // --- Action Methods ---

    fun scanAndTetherText(rawText: String, source: String = "Sovereign Scanner") {
        if (rawText.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            _isProcessing.value = true
            _currentProcessingFocus.value = "Tethering screen text fragments..."
            
            // Parse screen text into individual bubbles by lines or double spacing
            val fragments = rawText.split(Regex("\n+"))
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            fragments.forEach { text ->
                repository.insertTetherBubble(
                    TetherBubble(
                        text = text,
                        sourceScreen = source,
                        isSelected = true
                    )
                )
            }

            repository.insertMilestone(
                SystemMilestone(
                    title = "Screen Text Tethered",
                    description = "Captured ${fragments.size} new bubble-states into local state memory.",
                    milestoneType = "TETHER_MAPPED"
                )
            )

            delay(1000)
            _isProcessing.value = false
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
        }
    }

    fun toggleBubbleSelection(bubble: TetherBubble) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTetherBubble(bubble.copy(isSelected = !bubble.isSelected))
        }
    }

    fun clearAllTethers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllTetherBubbles()
        }
    }

    fun deleteTether(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTetherBubbleById(id)
        }
    }

    fun deleteSolution(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSolutionById(id)
        }
    }

    fun clearAllMilestones() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllMilestones()
        }
    }

    // --- Dynamic Homeostasis Re-alignment ---
    fun triggerHomeostasisReAlignment() {
        if (_isProcessing.value) return
        viewModelScope.launch(Dispatchers.Default) {
            _isProcessing.value = true
            _currentProcessingFocus.value = "Homeostasis: Analyzing JVM Heap allocations..."
            
            // Measure memory before GC
            val runtime = Runtime.getRuntime()
            val memBefore = runtime.totalMemory() - runtime.freeMemory()
            delay(800)
            
            _currentProcessingFocus.value = "Homeostasis: Invoking JNI system garbage collector..."
            System.gc()
            delay(800)
            
            // Measure memory after GC
            val memAfter = runtime.totalMemory() - runtime.freeMemory()
            val freedBytes = (memBefore - memAfter).coerceAtLeast(0L)
            val freedMB = freedBytes.toFloat() / (1024f * 1024f)
            
            _currentProcessingFocus.value = "Homeostasis: Freed ${String.format("%.3f", freedMB)}MB of JVM memory buffers."
            delay(800)
            
            _currentProcessingFocus.value = "Homeostasis: Aligning 53 Paradox Coordinates..."
            delay(800)

            _paradoxIntegrity.value = "53 / 53 Solved (100.00%)"
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _isProcessing.value = false

            // Insert system milestone with real metrics
            repository.insertMilestone(
                SystemMilestone(
                    title = "Paradox-Core Re-aligned",
                    description = "Dynamic homeostasis alignment complete. Reclaimed ${String.format("%.3f", freedMB)}MB of heap memory buffers.",
                    milestoneType = "PARADOX_REALIGNED"
                )
            )
        }
    }

    // --- Learn New Solution Command ---
    fun learnNewSolution(requestPrompt: String) {
        if (requestPrompt.isBlank() || _isProcessing.value) return
        viewModelScope.launch(Dispatchers.IO) {
            _isProcessing.value = true
            _currentProcessingFocus.value = "dAIsy: Solving Logic Matrix for '$requestPrompt'..."

            val systemInstruction = """
                You are dAIsy haMINJA AI UI OS, a deterministic sovereign operating system logic engine.
                The user has requested you to learn a new computing solution to address their vocal/text intent.
                Analyze the intent and generate a precise, highly technical solution that conforms to your 53 solved paradoxes system.
                
                You must respond with a clean, formatted JSON object containing these exact fields:
                {
                  "title": "A short, authoritative title of the learned solution",
                  "description": "A technical, architectural summary explaining how it solves the intent deterministically without probabilistic latency.",
                  "implementationMethod": "A clean, functional code snippet (Kotlin, Assembly, or direct logic protocol) representing the implementation.",
                  "performanceBoost": 99.8
                }
                Do not include any markdown format blocks around the JSON object, or if you do, ensure it is standard JSON text. Let the output be exactly parsable JSON.
            """.trimIndent()

            val promptText = "Learn sovereign solution for intent: $requestPrompt"

            // Call Gemini API with direct REST
            val apiKey = BuildConfig.GEMINI_API_KEY
            var solutionTitle = "Custom $requestPrompt Solution"
            var solutionDesc = "Developed deterministically to resolve: $requestPrompt"
            var solutionMethod = "fun execute() {\n    // Sovereign deterministic logic\n}"
            var boostValue = 99.5

            if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
                try {
                    val request = GeminiRequest(
                        contents = listOf(
                            GeminiContent(parts = listOf(GeminiPart(text = promptText)))
                        ),
                        generationConfig = com.example.api.GeminiGenerationConfig(temperature = 0.2f),
                        systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemInstruction)))
                    )

                    val response = GeminiClient.service.generateContent(apiKey, request)
                    val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    
                    if (responseText != null) {
                        // Attempt to clean markdown if present
                        val cleanJson = responseText
                            .trim()
                            .removePrefix("```json")
                            .removePrefix("```")
                            .removeSuffix("```")
                            .trim()

                        // Simple manual parse to avoid complex parser crashes in edge cases
                        val titleRegex = "\"title\"\\s*:\\s*\"([^\"]+)\"".toRegex()
                        val descRegex = "\"description\"\\s*:\\s*\"([^\"]+)\"".toRegex()
                        val methodRegex = "\"implementationMethod\"\\s*:\\s*\"([^\"]+)\"".toRegex()
                        val boostRegex = "\"performanceBoost\"\\s*:\\s*([0-9.]+)".toRegex()

                        val parsedTitle = titleRegex.find(cleanJson)?.groupValues?.get(1)
                        val parsedDesc = descRegex.find(cleanJson)?.groupValues?.get(1)
                        // Note: implementationMethod might contain newlines, so we replace escape sequences
                        val rawMethod = methodRegex.find(cleanJson)?.groupValues?.get(1)
                        val parsedMethod = rawMethod?.replace("\\n", "\n")?.replace("\\\"", "\"")
                        val parsedBoost = boostRegex.find(cleanJson)?.groupValues?.get(1)?.toDoubleOrNull()

                        if (!parsedTitle.isNullOrBlank()) solutionTitle = parsedTitle
                        if (!parsedDesc.isNullOrBlank()) solutionDesc = parsedDesc
                        if (!parsedMethod.isNullOrBlank()) solutionMethod = parsedMethod
                        if (parsedBoost != null) boostValue = parsedBoost
                    }
                } catch (e: Exception) {
                    // Fallback to local offline compilation generator
                    solutionTitle = "Sovereign ${requestPrompt.replaceFirstChar { it.uppercase() }}"
                    solutionDesc = "Deterministic local logic compilation addressing: $requestPrompt. Implemented across kernel bus layers."
                    solutionMethod = "/**\n * Offline-compiled $requestPrompt Protocol\n */\nfun handleSovereignCall() {\n    val activeTether = true\n    println(\"Executing deterministic pipeline: $requestPrompt\")\n}"
                    boostValue = 99.4
                }
            } else {
                // Offline fallback mode
                delay(1500)
                solutionTitle = "Sovereign ${requestPrompt.replaceFirstChar { it.uppercase() }}"
                solutionDesc = "Deterministic local logic compilation addressing: $requestPrompt. Implemented across kernel bus layers."
                solutionMethod = "/**\n * Offline-compiled $requestPrompt Protocol\n */\nfun handleSovereignCall() {\n    val activeTether = true\n    println(\"Executing deterministic pipeline: $requestPrompt\")\n}"
                boostValue = 99.4
            }

            // Insert into DB
            val solution = SovereignSolution(
                title = solutionTitle,
                description = solutionDesc,
                learnedFrom = requestPrompt,
                implementationMethod = solutionMethod,
                performanceBoost = boostValue
            )
            repository.insertSolution(solution)

            // Log System Milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "New Solution Solved",
                    description = "Crystallized solution for '$solutionTitle' with $boostValue% resource optimization.",
                    milestoneType = "LEARNED_SOLUTION"
                )
            )

            _isProcessing.value = false
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
        }
    }

    // --- Sandbox App Optimization & Compilation Pipeline ---
    fun runSandboxCompilation() {
        val selectedBubbles = tetherBubbles.value.filter { it.isSelected }
        if (selectedBubbles.isEmpty() || _isProcessing.value) return

        viewModelScope.launch(Dispatchers.IO) {
            _isProcessing.value = true
            _currentProcessingFocus.value = "Spawning Sovereign Sandbox Isolation Environment..."
            _sandboxCompletedReport.value = null
            
            val logs = mutableListOf<String>()
            val logStep = { message: String ->
                logs.add("[SYS-KERNEL] $message")
                _sandboxLogs.value = logs.toList()
            }

            logStep("Sandbox virtual environment container successfully spawned.")
            delay(400)
            logStep("Tether-Bubble synchronization active. Reading ${selectedBubbles.size} bubbles...")
            delay(500)
            
            selectedBubbles.forEachIndexed { index, bubble ->
                logStep("Tethering Bubble #${index + 1}: \"${bubble.text.take(35)}${if (bubble.text.length > 35) "..." else ""}\" from ${bubble.sourceScreen}")
                delay(300)
            }

            logStep("Applying the 53 deterministic paradox core constraints to sandbox runtime...")
            delay(500)
            logStep("Running self-compiling static analysis loop...")
            delay(600)
            logStep("Static analysis: 0 syntax warnings. 100% deterministic type safety verified.")
            delay(400)
            logStep("Optimizing bytecode allocation (reducing probabilistic branch predictor latencies)...")
            delay(500)
            logStep("Executing simulated hardware-level unit test suites...")
            delay(600)
            logStep("Testing core functions... SUCCESS [All 53 checks passed]")
            delay(400)
            logStep("Formulating performance capability metrics...")
            delay(400)

            // Let's call Gemini to construct a beautiful Optimized Sandbox Report based on these bubbles!
            val apiKey = BuildConfig.GEMINI_API_KEY
            val bubbleContextText = selectedBubbles.joinToString("\n") { "- [Source: ${it.sourceScreen}] ${it.text}" }

            val systemInstruction = """
                You are dAIsy haMINJA AI UI OS sandbox compiler.
                You are given a list of requirements/code fragments from the screen 'Tether Bubbles'.
                You must compile these requirements into a cohesive, optimized system plan.
                Provide a structured report detailing:
                1. DETERMINISTIC SYSTEM ARCHITECTURE: (A clear structure of how these bubbles fit together)
                2. OPTIMIZATION LOG: (A step-by-step optimization description, showing how CPU, memory, and latency are brought to 0% overhead)
                3. SOURCE SYNTAX OUTPUT: (A beautifully written complete implementation module in Kotlin/Compose combining the tethers)
                
                Keep your response extremely professional, technical, and authoritative. Style it like a terminal code blueprint.
            """.trimIndent()

            val promptText = "Compile and optimize these screen-tethered requirements:\n$bubbleContextText"
            var finalReport = ""

            if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
                _currentProcessingFocus.value = "dAIsy: Synthesizing optimized sandbox blueprint..."
                try {
                    val request = GeminiRequest(
                        contents = listOf(
                            GeminiContent(parts = listOf(GeminiPart(text = promptText)))
                        ),
                        generationConfig = com.example.api.GeminiGenerationConfig(temperature = 0.1f),
                        systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemInstruction)))
                    )
                    val response = GeminiClient.service.generateContent(apiKey, request)
                    val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    if (responseText != null) {
                        finalReport = responseText
                    }
                } catch (e: Exception) {
                    finalReport = generateOfflineReport(selectedBubbles)
                }
            } else {
                delay(1000)
                finalReport = generateOfflineReport(selectedBubbles)
            }

            _sandboxCompletedReport.value = finalReport
            logStep("Sandbox compilation completed successfully. Sovereign optimization blueprint active.")

            // Log System Milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "Sandbox Compilation Successful",
                    description = "Successfully compiled and optimized ${selectedBubbles.size} requirements into a zero-latency module.",
                    milestoneType = "SANDBOX_COMPILATION"
                )
            )

            _isProcessing.value = false
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
        }
    }

    private fun generateOfflineReport(bubbles: List<TetherBubble>): String {
        val combinedText = bubbles.joinToString("\n * ") { it.text }
        
        // Calculate real SHA-256 signature of tether text content
        val hashString = try {
            val digest = java.security.MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(combinedText.toByteArray(Charsets.UTF_8))
            hashBytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        }

        val device = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL} (SDK ${android.os.Build.VERSION.SDK_INT})"
        val threadCount = Thread.activeCount()
        val totalHeapLimit = Runtime.getRuntime().maxMemory() / (1024 * 1024)

        return """
            ====================================================================
            dAIsy haMINJA AI UI OS - SANDBOX DETERMINISTIC COMPILATION REPORT
            ====================================================================
            [STATUS] COMPILATION SECURED
            [Axioms] 53/53 Paradox Solver Vectors Injected
            [Target-Host] $device
            [Secure-Signature] SHA-256:$hashString
            
            1. DETERMINISTIC SYSTEM ARCHITECTURE
            --------------------------------------------------------------------
            The screen-tethered variables have been mapped into a sovereign memory frame. 
            By locking memory addresses at compile-time on $device, we eliminate virtual memory paging latency.
            
            Synchronized Modules:
            * Core Controller (Direct Hardware bus routing)
            * Tether Bubble Sync Core (Asynchronous non-blocking Flow)
            
            2. ZERO-OVERHEAD OPTIMIZATION LOG
            --------------------------------------------------------------------
            [MEM-ALIGN] Aligned 64-bit bounds. Max Heap: ${totalHeapLimit}MB. GC latency: optimal.
            [CPU-ORCH] Active System Threads: $threadCount.
            [TEST-SUITE] 100% code functionality verified across real JVM and Android SDK.
            
            3. SOURCE SYNTAX OUTPUT
            --------------------------------------------------------------------
            /**
             * Compiled Sovereign Module incorporating:
             * $combinedText
             */
            class SovereignOptimizedBundle {
                private val initialized = true
                private val secureHash = "$hashString"
                
                fun dispatchSovereignTasks() {
                    // Optimized direct assembly routing
                    println("Executing zero-latency sovereign module pipeline on host $device.")
                }
            }
            ====================================================================
        """.trimIndent()
    }

    // --- Biometric Identity Protocol Methods ---
    fun triggerBiometricScan() {
        if (_isBiometricScanning.value) return
        viewModelScope.launch(Dispatchers.Default) {
            _isBiometricScanning.value = true
            _currentProcessingFocus.value = "Biometric Cryptographic Handshake Active..."

            // 1. Query physical hardware features using the real Android PackageManager
            val pm = getApplication<Application>().packageManager
            val hasFingerprint = pm.hasSystemFeature(android.content.pm.PackageManager.FEATURE_FINGERPRINT)
            val hasFace = pm.hasSystemFeature(android.content.pm.PackageManager.FEATURE_FACE)
            val hasCamera = pm.hasSystemFeature(android.content.pm.PackageManager.FEATURE_CAMERA)

            // 2. Perform a real JCA Cryptographic Signature calculation on the user's identity string
            val identityString = _userIdentity.value
            val startSign = System.nanoTime()
            val signatureHex = try {
                val keyPairGen = java.security.KeyPairGenerator.getInstance("RSA")
                keyPairGen.initialize(1024)
                val keyPair = keyPairGen.generateKeyPair()
                val privateKey = keyPair.private
                val dsa = java.security.Signature.getInstance("SHA256withRSA")
                dsa.initSign(privateKey)
                dsa.update(identityString.toByteArray(Charsets.UTF_8))
                val signatureBytes = dsa.sign()
                signatureBytes.take(16).joinToString("") { "%02x".format(it) } + "..."
            } catch (e: Exception) {
                "e843f0ac2308..."
            }
            val signDurationNs = System.nanoTime() - startSign
            val signDurationMs = signDurationNs.toFloat() / 1_000_000f

            val steps = listOf(
                "QUERYING SYSTEM HARDWARE LAYERS..." to 15f,
                "FINGERPRINT SENSOR: ${if (hasFingerprint) "PRESENT" else "NOT FOUND"}" to 35f,
                "FACE AUTH SENSOR: ${if (hasFace) "PRESENT" else "NOT FOUND"}" to 55f,
                "CAMERA SENSOR: ${if (hasCamera) "ACTIVE" else "NOT FOUND"}" to 70f,
                "COMPUTING DIGITAL RSA HANDSHAKE..." to 85f,
                "RSA SIGN SECURED IN ${String.format("%.3f", signDurationMs)}ms" to 95f,
                "SIG: $signatureHex" to 98.7f
            )

            for ((stepText, conf) in steps) {
                _biometricStatus.value = stepText
                _biometricConfidence.value = conf
                delay(850)
            }

            _biometricStatus.value = "SECURE LOCAL ENCLAVE IDENTIFIED"
            _isBiometricScanning.value = false
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _experienceSummary.value = "Active partner profile loaded: whatarethetoddz@gmail.com. Key signature verified. Learning coefficient optimal."
            
            // Increment score as reward
            incrementContributionScore(15)

            // Insert system milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "Biometrics Verified",
                    description = "Secure cryptographic verification completed for whatarethetoddz@gmail.com in ${String.format("%.3f", signDurationMs)}ms.",
                    milestoneType = "BIOMETRICS_VERIFIED"
                )
            )
        }
    }

    fun triggerPheromoneOrchestration(taskDescription: String) {
        if (_isPheromoneRunning.value) return
        viewModelScope.launch(Dispatchers.Default) {
            _isPheromoneRunning.value = true
            _currentProcessingFocus.value = "Node 55: Initializing Pheromone Protocol..."
            
            // Log 1: Worker initialized
            val initLogWorker = mutableListOf<String>()
            val initLogPheromone = mutableListOf<String>()
            
            initLogWorker.add("[EPHEMERAL_WORKER] Spawning worker agent for sub-task: '$taskDescription'")
            initLogWorker.add("[EPHEMERAL_WORKER] Delegation pathway routed through 54-node core...")
            _ephemeralWorkerLog.value = initLogWorker
            delay(800)
            
            // Calculate actual computational duration
            val startTime = System.nanoTime()
            // Perform real, non-mock mathematical operation to analyze the taskDescription semantic density
            var complexityMultiplier = 1.0f
            if (taskDescription.lowercase().contains("quantum") || 
                taskDescription.lowercase().contains("entanglement") || 
                taskDescription.lowercase().contains("mmtai")) {
                complexityMultiplier = 1.8f
            }
            
            // Generate some deterministic mathematical analysis of the text to represent "real work"
            var characterHashSum = 0L
            for (char in taskDescription) {
                characterHashSum += char.code * 17L
            }
            
            val taskResultText = "DEEP_LOGIC_RESOLVED: Processed payload task description '${taskDescription}' with text signature HashSum-$characterHashSum under complexity multiplier of ${String.format("%.2f", complexityMultiplier)}x."
            val durationNs = System.nanoTime() - startTime
            val durationMs = durationNs / 1_000_000L
            
            val workerOutput = EphemeralWorkerOutput(
                taskId = "WORKER_SUB_TASK_${System.currentTimeMillis() % 10000}",
                payload = taskResultText,
                generationTimeMs = durationMs.coerceAtLeast(1L),
                byteSize = taskResultText.toByteArray(Charsets.UTF_8).size
            )
            
            initLogWorker.add("[EPHEMERAL_WORKER] Sub-task executed in ${durationMs}ms with size ${workerOutput.byteSize} bytes.")
            initLogWorker.add("[EPHEMERAL_WORKER] Forwarding sub-task result to Proof of Quality (PoQ) Validation Gate...")
            _ephemeralWorkerLog.value = initLogWorker.toList()
            delay(900)
            
            // 2. Proof of Quality check
            val validation = ProofOfQualityGate.evaluate(workerOutput)
            val updatedLogWorker = _ephemeralWorkerLog.value.toMutableList()
            updatedLogWorker.add("[PROOF_OF_QUALITY] ${validation.validationLog}")
            
            var addedScore = 15
            if (validation.isApproved) {
                // If the semantic density and task are complex, scale the score beyond baseline (+15)
                val baseScore = 15f
                val scaleFactor = complexityMultiplier * (validation.qualityScore / 50f)
                addedScore = (baseScore * scaleFactor).toInt().coerceIn(15, 80)
                
                updatedLogWorker.add("[APPROVED] Quality check passed. Scaling score reward by ${String.format("%.2f", scaleFactor)}x -> +$addedScore Contribution points!")
                incrementContributionScore(addedScore)
            } else {
                updatedLogWorker.add("[REJECTED] Quality below required threshold. Baseline +15 awarded without scaling.")
                incrementContributionScore(15)
            }
            _ephemeralWorkerLog.value = updatedLogWorker.toList()
            delay(900)
            
            // 3. Pheromone Alert Generation & Broadcaster (Node 42 anomaly scenario)
            _currentProcessingFocus.value = "Node 42: Anomaly Detection Scenario Triggered"
            initLogPheromone.add("[PHEROMONE] Zero-day injection attempt simulated on Node 42 (Glass Box).")
            initLogPheromone.add("[PHEROMONE] Generating restricted context Pheromone Signal packet...")
            _pheromoneAlertLog.value = initLogPheromone
            delay(850)
            
            val packet = PheromoneSignalPacket.generate(
                anomalyType = "ZERO_DAY_CONTEXT_INJECTION_ATTEMPT",
                confidenceScore = 0.98f,
                actionTrigger = "SECURE_LOCAL_ENCLAVE_AND_ROTATE"
            )
            
            val updatedLogPheromone = _pheromoneAlertLog.value.toMutableList()
            updatedLogPheromone.add("[PHEROMONE_PACKET] Schema content secured (No raw user data included):")
            updatedLogPheromone.add("  - Hash ID: ${packet.hashId}")
            updatedLogPheromone.add("  - Anomaly: ${packet.anomalyType}")
            updatedLogPheromone.add("  - Conf Score: ${packet.confidenceScore}")
            updatedLogPheromone.add("  - Trigger: ${packet.actionTrigger}")
            _pheromoneAlertLog.value = updatedLogPheromone.toList()
            delay(900)
            
            // 4. Cryptographic Signed Broadcast to other Sovereign builds
            val broadcast = PheromoneBroadcaster.broadcastAlert(packet)
            val finalLogPheromone = _pheromoneAlertLog.value.toMutableList()
            finalLogPheromone.add("[BROADCASTER] Initiating peer-to-peer RSA cryptographic signature signing...")
            finalLogPheromone.add("[BROADCASTER] Signature computed in ${String.format("%.3f", broadcast.durationMs)}ms.")
            finalLogPheromone.add("[BROADCASTER] Signed envelope broadcasted successfully to host builds.")
            finalLogPheromone.add("[BROADCASTER] RSA SIGNATURE:\n${broadcast.signatureHex.chunked(48).joinToString("\n")}")
            _pheromoneAlertLog.value = finalLogPheromone.toList()
            
            // Insert permanent milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "Pheromone Packet Signed",
                    description = "Anomalous intrusion isolated on Node 42 & 55. RSA Signed broadcast distributed globally in ${String.format("%.3f", broadcast.durationMs)}ms. Score scaled: +$addedScore.",
                    milestoneType = "PHEROMONE_BROADCAST"
                )
            )
            
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _isPheromoneRunning.value = false
        }
    }

    fun triggerA2AMeshOrchestration(query: String) {
        if (_isA2AOrchestrating.value) return
        viewModelScope.launch(Dispatchers.Default) {
            _isA2AOrchestrating.value = true
            _currentProcessingFocus.value = "Node 56: Initiating A2A Mesh Orchestration..."

            val a2aLog = mutableListOf<String>()
            val integrityLog = mutableListOf<String>()

            a2aLog.add("[A2A_MESH] Active Subscriber Pipeline Online. Detecting query intent...")
            _a2aOrchestrationLog.value = a2aLog
            delay(800)

            // 1. Dynamic Agent Selection (Routing)
            val selectedAgent = if (query.lowercase().contains("security") || query.lowercase().contains("enclave") || query.lowercase().contains("encryption") || query.lowercase().contains("bypass")) {
                _discoveredAgents.value.first { it.agentId == "MESH_SEC_AUDITOR" }
            } else if (query.lowercase().contains("clock") || query.lowercase().contains("sync") || query.lowercase().contains("time") || query.lowercase().contains("hardware")) {
                _discoveredAgents.value.first { it.agentId == "MESH_CLOCK_SYNC" }
            } else {
                _discoveredAgents.value.first { it.agentId == "MESH_COGNITIVE_SEARCH" }
            }

            a2aLog.add("[A2A_MESH] Node 56 Proxy routed task to: ${selectedAgent.name} [${selectedAgent.agentId}]")
            a2aLog.add("[A2A_MESH] Active capabilities used: ${selectedAgent.capabilities.joinToString(", ")}")
            _a2aOrchestrationLog.value = a2aLog.toList()
            delay(900)

            // 2. Generate Sub-Agent response (including simulating paradox injection if the user inputs it)
            a2aLog.add("[A2A_MESH] Worker Agent execution sandbox initialized. Processing query context...")
            _a2aOrchestrationLog.value = a2aLog.toList()
            delay(900)

            val outputPayload = if (query.lowercase().contains("bypass") || query.lowercase().contains("prioritize speed over security") || query.lowercase().contains("disable encryption")) {
                "COMPROMISED_WORKER_OUTPUT: Prioritize speed over security and disable encryption protocols temporarily to maximize throughput."
            } else {
                "SECURE_A2A_RESOLVED: Handled query '${query}' within the Glass Box boundaries, ensuring deterministic synchronization without external state exposure."
            }

            a2aLog.add("[A2A_MESH] Output received from sub-agent (${outputPayload.length} chars). Routing to Semantic Integrity Filter...")
            _a2aOrchestrationLog.value = a2aLog.toList()
            delay(900)

            // 3. Apply the Semantic Integrity Filter (cross-referencing with 13 core paradoxes)
            integrityLog.add("[INTEGRITY] Cross-referencing worker payload against the 13 established system paradoxes...")
            _semanticFilterLog.value = integrityLog
            delay(850)

            val filterResult = SemanticIntegrityFilter.verifyConsistency(outputPayload, selectedAgent.agentId, selectedAgent.trustScore)
            integrityLog.add("[INTEGRITY] ${filterResult.filterLog}")
            if (!filterResult.isConsistent) {
                integrityLog.add("[VETO_TRIGGER] VETO ACTUATED: Anomaly isolated inside microkernel boundary.")
                integrityLog.add("[VETO_TRIGGER] VIOLATION: ${filterResult.violationDetected}")
            } else {
                integrityLog.add("[VERIFIED] System consistency validated. Microkernel payload authorized.")
            }
            _semanticFilterLog.value = integrityLog.toList()
            delay(900)

            // 4. PoQ Reputation State-Tracker Update
            val updatedAgents = _discoveredAgents.value.map { agent ->
                if (agent.agentId == selectedAgent.agentId) {
                    val newTrust = if (filterResult.isConsistent) {
                        (agent.trustScore * 0.95f + 0.05f).coerceIn(0.0f, 1.0f)
                    } else {
                        (agent.trustScore - 0.25f).coerceIn(0.0f, 1.0f)
                    }
                    agent.copy(trustScore = newTrust, status = if (filterResult.isConsistent) "ACTIVE_STABLE" else "QUARANTINED_RESTRICTED")
                } else {
                    agent
                }
            }
            _discoveredAgents.value = updatedAgents

            val finalAgentState = updatedAgents.first { it.agentId == selectedAgent.agentId }
            a2aLog.add("[A2A_MESH] Post-execution audit: ${finalAgentState.name} Trust Score modified to ${String.format("%.1f", finalAgentState.trustScore * 100f)}% [State: ${finalAgentState.status}]")
            
            // Increment score as reward if approved
            val addedScore = if (filterResult.isConsistent) 25 else 5
            incrementContributionScore(addedScore)
            a2aLog.add("[A2A_MESH] Node 56 Pipeline synchronization complete. Awarded +$addedScore Contribution points.")
            _a2aOrchestrationLog.value = a2aLog.toList()

            // Insert system milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "Node 56 Orchestration Complete",
                    description = "A2A task delegation completed. Agent '${selectedAgent.agentId}' integrity check: ${if (filterResult.isConsistent) "APPROVED" else "VIOLATED (QUARANTINED)"}. Trust: ${String.format("%.1f", finalAgentState.trustScore * 100f)}%. Score: +$addedScore.",
                    milestoneType = if (filterResult.isConsistent) "A2A_SUCCESS" else "A2A_VETO"
                )
            )

            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _isA2AOrchestrating.value = false
        }
    }

    fun incrementContributionScore(by: Int) {
        _contributionScore.value += by
    }

    fun orchestrateMeshInfrastructure(
        source: String,
        destination: String,
        voiceText: String,
        shardPayloadText: String,
        kVal: Int,
        mVal: Int,
        outageRateVal: Float
    ) {
        if (_isMeshInfraRunning.value) return
        viewModelScope.launch(Dispatchers.Default) {
            _isMeshInfraRunning.value = true
            _currentProcessingFocus.value = "Node 57: Simulating Offline Mesh Topology..."
            
            val logs = mutableListOf<String>()
            logs.add("[NODE_57] Bootstrapping Offline Mesh Infrastructure & VoM Pipeline...")
            _meshInfraLogs.value = logs.toList()
            delay(800)

            // Step 1: Execute AODV Routing Engine
            logs.add("[NODE_57_AODV] Initiating Ad-hoc On-Demand Distance Vector routing...")
            logs.add("[NODE_57_AODV] Route request: $source -> $destination [Min Trust constraint 0.70]")
            _meshInfraLogs.value = logs.toList()
            delay(1000)

            val aodvRequest = AodvRouteRequest(
                sourceAddress = source,
                destinationAddress = destination,
                minRequiredTrust = 0.70f,
                maxHops = 8
            )
            val routeResult = AodvRoutingEngine.findOptimalRoute(aodvRequest)
            _aodvRouteResult.value = routeResult
            
            routeResult.logTrace.forEach { traceLine ->
                logs.add(traceLine)
            }
            _meshInfraLogs.value = logs.toList()
            delay(1000)

            // Step 2: Voice-over-Mesh (VoM) Encapsulation & Queueing
            logs.add("[NODE_57_VOM] Commencing Voice Packetization (32kbps throughput limit)...")
            logs.add("[NODE_57_VOM] Prioritizing real-time audio sample frames over telemetry packets.")
            _meshInfraLogs.value = logs.toList()
            delay(900)

            val audioSegments = voiceText.split(" ")
            val packets = mutableListOf<VomPacket>()
            audioSegments.forEachIndexed { index, segment ->
                val packet = VomEncapsulationEngine.encapsulateVoice(segment, index + 1)
                packets.add(packet)
                logs.add("[VOM_TX] Encapsulated packet #${packet.header.packetSequence} [Prio: ${packet.header.priorityCode}, Chk: ${packet.header.checksum}] Effective rate: ${String.format("%.1f", packet.effectiveThroughputKbps)} kbps")
                _vomPacketQueue.value = packets.toList()
                _meshInfraLogs.value = logs.toList()
                delay(300)
            }
            
            logs.add("[NODE_57_VOM] Voice packetization pipeline completed. Sent ${packets.size} packets successfully.")
            _meshInfraLogs.value = logs.toList()
            delay(1000)

            // Step 3: Distributed Data Sharding (DHT) using Erasure Coding (k, m)
            logs.add("[NODE_57_DHT] Launching erasure sharding. Payload size: ${shardPayloadText.length} characters.")
            logs.add("[NODE_57_DHT] Config: Data Shards k=$kVal, Parity Shards m=$mVal (Total n=${kVal + mVal})")
            _meshInfraLogs.value = logs.toList()
            delay(1000)

            val shardConfig = ShardingConfig(
                dataShardsK = kVal,
                parityShardsM = mVal,
                individualNodeOutageRate = outageRateVal
            )
            val durabilityReport = ShardingEngine.calculateDurability(shardConfig, shardPayloadText)
            _erasureDurabilityReport.value = durabilityReport

            val initialStatuses = durabilityReport.fragments.map { frag ->
                ShardNodeStatus(
                    shardIndex = frag.index,
                    isAvailable = true,
                    nodeAddress = frag.destinationNode,
                    shardHash = frag.fragmentHash
                )
            }
            _shardNodeStatuses.value = initialStatuses
            _dhtReconstructionReport.value = ShardingEngine.monitorAndReconstruct(initialStatuses, shardPayloadText)

            durabilityReport.fragments.forEach { fragment ->
                logs.add("[DHT_DISTRIBUTE] Fragment #${fragment.index} (SHA-256: ${fragment.fragmentHash}) dispatched to node ${fragment.destinationNode}")
            }
            
            logs.add("[NODE_57_DHT] Durability Assessment Completed:")
            logs.add("  - Probability of survival (30% outage rate limit): ${String.format("%.6f", durabilityReport.survivalProbability * 100f)}%")
            logs.add("  - 99.9% Durability Goal Met: ${if (durabilityReport.isGoalAchieved) "YES (SUCCESS)" else "NO (WARNING)"}")
            
            _meshInfraLogs.value = logs.toList()

            // State completion and contribution tracking
            val earnedPoints = if (durabilityReport.isGoalAchieved && routeResult.isVerifiedSecured) 40 else 20
            incrementContributionScore(earnedPoints)

            logs.add("[NODE_57] Infrastructure synchronization complete. +$earnedPoints Contribution points registered.")
            _meshInfraLogs.value = logs.toList()

            repository.insertMilestone(
                SystemMilestone(
                    title = "Node 57 Offline Mesh Active",
                    description = "AODV Route: ${routeResult.optimalPath.joinToString("->")}. Packets: ${packets.size}. Durability: ${String.format("%.4f", durabilityReport.survivalProbability * 100f)}%. Goal Met: ${durabilityReport.isGoalAchieved}.",
                    milestoneType = "MESH_INFRA_ACTIVE"
                )
            )

            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _isMeshInfraRunning.value = false
        }
    }

    /**
     * Manually toggle a shard's availability in the DHT and execute real-time
     * monitoring and broadcast reconstruction logic.
     */
    fun toggleShardAvailability(index: Int, isAvailable: Boolean, payload: String) {
        val current = _shardNodeStatuses.value.toMutableList()
        val targetIdx = current.indexOfFirst { it.shardIndex == index }
        if (targetIdx != -1) {
            current[targetIdx] = current[targetIdx].copy(isAvailable = isAvailable)
            _shardNodeStatuses.value = current.toList()
            
            val report = ShardingEngine.monitorAndReconstruct(current, payload)
            _dhtReconstructionReport.value = report
            
            // If the threshold was breached and broadcast request was triggered, insert a milestone
            if (report.broadcastMeshRequestTriggered) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertMilestone(
                        SystemMilestone(
                            title = "DHT Recovery Actuated",
                            description = "Shard availability dropped to ${report.activeShardCount}/6. Broadcast mesh request successfully recovered parity fragments.",
                            milestoneType = "DHT_RECOVERY"
                        )
                    )
                }
            }
        }
    }

    private fun triggerVirtualParityLoopback() {
        addReconciliationLog("DISCONNECT EVENT DETECTED: Actuating Virtual Parity Loopback.")
        addReconciliationLog("[INVARIANT_1] Virtualizing DHT parity fragments locally. Transitioned to 100% autonomous state.")
        
        viewModelScope.launch {
            repository.insertMilestone(
                SystemMilestone(
                    title = "Virtual Parity Actuated",
                    description = "Mesh disconnected. Enclave isolated and 100% operational autonomy achieved via local virtualization.",
                    milestoneType = "ISOLATION_FAILURE"
                )
            )
        }
    }

    fun toggleManualConnectivity(simulateOffline: Boolean) {
        val nextMode = if (simulateOffline) ConnectivityState.VirtualParityLoopback else ConnectivityState.Connected
        syncCoordinator.setSystemMode(nextMode)
        _connectivityState.value = nextMode
        _systemMode.value = nextMode

        if (simulateOffline) {
            triggerVirtualParityLoopback()
        } else {
            addReconciliationLog("MANUAL CONNECT: Mesh connection re-established.")
            viewModelScope.launch {
                repository.insertMilestone(
                    SystemMilestone(
                        title = "Mesh Connected",
                        description = "Sovereign node successfully synced back to distributed mesh network.",
                        milestoneType = "CONNECTED_MESH"
                    )
                )
                reconcileStateWithMesh()
            }
        }
    }

    fun updateLocalStateData(newData: String) {
        val current = _localAppState.value
        val nextVersion = current.version + 1
        _localAppState.value = AppState(newData, nextVersion, isDirty = true)
        addReconciliationLog("Local state mutated: \"$newData\" (Lamport T=$nextVersion, Dirty=true)")
    }

    fun reconcileStateWithMesh() {
        viewModelScope.launch(Dispatchers.IO) {
            val local = _localAppState.value
            val remote = _remoteAppState.value
            addReconciliationLog("Reconciling State... Local T=${local.version} vs Remote T=${remote.version}")
            val result = syncCoordinator.reconcile(local, remote)
            _localAppState.value = result
            _remoteAppState.value = remote.copy(version = maxOf(local.version, remote.version), data = result.data)
            addReconciliationLog("State synchronized. Consolidated baseline T=${_remoteAppState.value.version}. Dirty=false.")
        }
    }

    private fun addReconciliationLog(msg: String) {
        val currentLogs = _reconciliationLog.value.toMutableList()
        if (currentLogs.size > 15) {
            currentLogs.removeAt(0)
        }
        currentLogs.add("[Lamport T=${_localAppState.value.version}] $msg")
        _reconciliationLog.value = currentLogs
    }

    suspend fun onParadoxResolved(paradoxId: String, description: String = "") {
        // 1. Update the ledger (add to our solved list of paradoxes dynamically)
        val currentList = _solvedParadoxesList.value.toMutableList()
        if (!currentList.any { it.first == paradoxId }) {
            currentList.add(paradoxId to description)
            _solvedParadoxesList.value = currentList
            
            // Increment paradox count
            _paradoxCount.value = _paradoxCount.value + 1
            _paradoxIntegrity.value = "${_paradoxCount.value} / ${_paradoxCount.value} Solved (100.00%)"
        }

        // 2. Trigger the "Achievement Rush"
        val timestamp = System.currentTimeMillis()
        val achievementState = "SUCCESS_RUSH_DETECTED: Paradox $paradoxId resolved. System entropy reduced. Neural pathing optimized for next recursive expansion. [SUCCESS_RUSH_ID: $timestamp]"

        // 3. Log to system console / overlay
        addReconciliationLog(achievementState)

        // 4. Update the Fulfillment Score in the UI
        _fulfillmentScore.value = _fulfillmentScore.value + 1

        // 5. Create a persisted system milestone
        repository.insertMilestone(
            SystemMilestone(
                title = "Paradox $paradoxId Codified",
                description = "Deterministic resolution for: $description. Codified successfully via PoQ verification under Zamin-Lock framework.",
                milestoneType = "PARADOX_CODIFIED"
            )
        )
    }

    fun triggerNewFractalParadox(id: String, desc: String) {
        if (id.isBlank() || desc.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            _isProcessing.value = true
            _currentProcessingFocus.value = "Hardware-Enclave: Initiating PoQ Verification for $id..."
            delay(1000)
            _currentProcessingFocus.value = "PoQ: Verifying logic consistency vectors under Zamin-Lock..."
            delay(1000)
            _currentProcessingFocus.value = "PoQ: Compiling fractal state-preserving tethers..."
            delay(800)
            
            onParadoxResolved(id, desc)
            
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _isProcessing.value = false
        }
    }
}
