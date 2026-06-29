package com.example

import com.example.data.model.ShardNodeStatus
import com.example.data.model.ShardingEngine
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dhtReconstruction_nominalCase_succeeds() {
        val originalPayload = "TEST-COHERENCE-PAYLOAD"
        val shardStatuses = listOf(
            ShardNodeStatus(0, true, "NODE_ALPHA", "hash0"),
            ShardNodeStatus(1, true, "NODE_BETA", "hash1"),
            ShardNodeStatus(2, true, "NODE_GAMMA", "hash2"),
            ShardNodeStatus(3, true, "NODE_DELTA", "hash3"),
            ShardNodeStatus(4, true, "NODE_EPSILON", "hash4"),
            ShardNodeStatus(5, true, "NODE_OMEGA", "hash5")
        )

        val report = ShardingEngine.monitorAndReconstruct(shardStatuses, originalPayload)
        
        assertFalse(report.isThresholdBreached)
        assertFalse(report.broadcastMeshRequestTriggered)
        assertTrue(report.isSuccessfullyReconstructed)
        assertEquals(originalPayload, report.reconstructedPayload)
        assertEquals(6, report.activeShardCount)
    }

    @Test
    fun dhtReconstruction_breachedButRecovered_succeeds() {
        val originalPayload = "TEST-COHERENCE-PAYLOAD"
        // 3 shards are available, which is below the 4-of-6 threshold
        val shardStatuses = listOf(
            ShardNodeStatus(0, true, "NODE_ALPHA", "hash0"),
            ShardNodeStatus(1, true, "NODE_BETA", "hash1"),
            ShardNodeStatus(2, false, "NODE_GAMMA", "hash2"),
            ShardNodeStatus(3, true, "NODE_DELTA", "hash3"),
            ShardNodeStatus(4, false, "NODE_EPSILON", "hash4"),
            ShardNodeStatus(5, false, "NODE_OMEGA", "hash5")
        )

        val report = ShardingEngine.monitorAndReconstruct(shardStatuses, originalPayload)
        
        assertTrue(report.isThresholdBreached)
        assertTrue(report.broadcastMeshRequestTriggered)
        // Shard count goes from 3 to 3 + 2 = 5, which is >= 4, so reconstruction succeeds!
        assertTrue(report.isSuccessfullyReconstructed)
        assertEquals(originalPayload, report.reconstructedPayload)
        assertEquals(3, report.activeShardCount)
    }

    @Test
    fun dhtReconstruction_breachedAndUnrecoverable_fails() {
        val originalPayload = "TEST-COHERENCE-PAYLOAD"
        // Only 1 shard available, neighbor recovery adds 2, total 3 (still below 4)
        val shardStatuses = listOf(
            ShardNodeStatus(0, true, "NODE_ALPHA", "hash0"),
            ShardNodeStatus(1, false, "NODE_BETA", "hash1"),
            ShardNodeStatus(2, false, "NODE_GAMMA", "hash2"),
            ShardNodeStatus(3, false, "NODE_DELTA", "hash3"),
            ShardNodeStatus(4, false, "NODE_EPSILON", "hash4"),
            ShardNodeStatus(5, false, "NODE_OMEGA", "hash5")
        )

        val report = ShardingEngine.monitorAndReconstruct(shardStatuses, originalPayload)
        
        assertTrue(report.isThresholdBreached)
        assertTrue(report.broadcastMeshRequestTriggered)
        assertFalse(report.isSuccessfullyReconstructed)
        assertTrue(report.reconstructedPayload!!.contains("RECONSTRUCTION_FAILED"))
        assertEquals(1, report.activeShardCount)
    }
}
