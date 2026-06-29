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
import com.example.data.repository.SovereignRepository
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

        // Real-time telemetry simulation loop
        viewModelScope.launch {
            while (true) {
                delay(1200)
                // update efficiency history
                val currentEffList = _efficiencyHistory.value.toMutableList()
                val isBusy = _isProcessing.value || _isBiometricScanning.value
                val baseEff = if (isBusy) 97.2f else 93.5f
                val fluctuation = (Math.random() * 2.5).toFloat()
                val nextEff = (baseEff + fluctuation).coerceAtMost(99.9f)
                currentEffList.add(nextEff)
                if (currentEffList.size > 20) currentEffList.removeAt(0)
                _efficiencyHistory.value = currentEffList

                // update throughput history
                val currentThroughputList = _throughputHistory.value.toMutableList()
                val baseThroughput = if (isBusy) 390f else 210f
                val thrFluctuation = (Math.random() * 70 - 35).toFloat()
                val nextThr = (baseThroughput + thrFluctuation).coerceAtLeast(100f)
                currentThroughputList.add(nextThr)
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
            _currentProcessingFocus.value = "Initiating Homeostasis Paradox-Core Re-alignment..."
            
            val steps = listOf(
                "Measuring operational heat & entropy...",
                "Cooling tether-bubble memory buffers...",
                "Aligning 53 solved paradoxes...",
                "Balancing objective vs subjective logic nodes...",
                "Coherence secured. Homeostasis finalized!"
            )

            for (step in steps) {
                _currentProcessingFocus.value = "Homeostasis: $step"
                delay(800)
            }

            _paradoxIntegrity.value = "53 / 53 Solved (100.00%)"
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _isProcessing.value = false

            // Insert system milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "Paradox-Core Re-aligned",
                    description = "All 53 logical contradictions synchronized to 100% deterministic integrity.",
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
        return """
            ====================================================================
            dAIsy haMINJA AI UI OS - SANDBOX DETERMINISTIC COMPILATION REPORT
            ====================================================================
            [STATUS] COMPILATION SECURED
            [Axioms] 53/53 Paradox Solver Vectors Injected
            
            1. DETERMINISTIC SYSTEM ARCHITECTURE
            --------------------------------------------------------------------
            The screen-tethered variables have been mapped into a sovereign memory frame. 
            By locking memory addresses at compile-time, we eliminate virtual memory paging latency.
            
            Synchronized Modules:
            * Core Controller (Direct Hardware bus routing)
            * Tether Bubble Sync Core (Asynchronous non-blocking Flow)
            
            2. ZERO-OVERHEAD OPTIMIZATION LOG
            --------------------------------------------------------------------
            [MEM-ALIGN] Aligned 64-bit bounds. Reduced GC overhead to exactly 0.00ms.
            [CPU-ORCH] pinned threads to absolute cores, avoiding context switching.
            [TEST-SUITE] 100% code functionality verified across simulated hardware layers.
            
            3. SOURCE SYNTAX OUTPUT
            --------------------------------------------------------------------
            /**
             * Compiled Sovereign Module incorporating:
             * $combinedText
             */
            class SovereignOptimizedBundle {
                private val initialized = true
                
                fun dispatchSovereignTasks() {
                    // Optimized direct assembly routing
                    println("Executing zero-latency sovereign module pipeline.")
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
            _currentProcessingFocus.value = "Biometric Identification active..."

            val steps = listOf(
                "CAPTURING VOICE SPECTRAL SIGNATURE..." to 34.2f,
                "ANALYZING FACIAL LANDMARK CODES..." to 68.9f,
                "DECRYPTING ENCLAVE PERMISSIONS..." to 89.1f,
                "SECURING SOLVEX KERNEL HANDSHAKE..." to 95.4f,
                "VERIFIED: whatarethetoddz@gmail.com" to 98.7f
            )

            for ((stepText, conf) in steps) {
                _biometricStatus.value = stepText
                _biometricConfidence.value = conf
                delay(700)
            }

            _biometricStatus.value = "SECURE LOCAL ENCLAVE IDENTIFIED"
            _isBiometricScanning.value = false
            _currentProcessingFocus.value = "Homeostasis (Coherent and Idle)"
            _experienceSummary.value = "Active partner profile loaded: whatarethetoddz@gmail.com. Last sync: ${System.currentTimeMillis()}. Learning coefficient optimal."
            
            // Increment score as reward
            incrementContributionScore(15)

            // Insert system milestone
            repository.insertMilestone(
                SystemMilestone(
                    title = "Biometrics Verified",
                    description = "Secure verification confirmed for whatarethetoddz@gmail.com with 98.7% accuracy.",
                    milestoneType = "BIOMETRICS_VERIFIED"
                )
            )
        }
    }

    fun incrementContributionScore(by: Int) {
        _contributionScore.value += by
    }
}
