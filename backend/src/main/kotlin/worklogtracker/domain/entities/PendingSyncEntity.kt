package worklogtracker.domain.entities

import worklogtracker.domain.entities.enums.SyncStatus
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime
import java.util.*

/**
 * PendingSync Aggregate Root
 * 
 * Queue of operations awaiting synchronization to server.
 * Supports offline-first architecture.
 * 
 * Domain Rules:
 * - Only pending syncs should be processed
 * - Failed syncs should be retried with backoff
 * - Synced operations should be cleaned up after retention period
 */
data class PendingSyncEntity(
    val id: Int? = null,
    val userId: UserId,
    val entityType: String,  // WORK_LOG, TIMER_SESSION
    val entityId: UUID,
    val operation: String,   // CREATE, UPDATE, DELETE
    val payload: String,     // JSON serialized data
    val syncStatus: SyncStatus,
    val syncError: String? = null,
    val createdAt: LocalDateTime,
    val syncedAt: LocalDateTime? = null,
    val lastModified: LocalDateTime? = null
) {
    
    /**
     * Mark as successfully synced
     */
    fun markAsSynced(): PendingSyncEntity = this.copy(
        syncStatus = SyncStatus.SYNCED,
        syncedAt = LocalDateTime.now()
    )

    /**
     * Mark as failed with error message
     */
    fun markAsFailed(error: String): PendingSyncEntity = this.copy(
        syncStatus = SyncStatus.FAILED,
        syncError = error
    )

    /**
     * Retry failed sync
     */
    fun retry(): PendingSyncEntity = this.copy(
        syncStatus = SyncStatus.PENDING,
        syncError = null
    )

    /**
     * Check if this sync is ready to retry
     */
    fun shouldRetry(): Boolean = syncStatus == SyncStatus.FAILED && lastModified != null
}

