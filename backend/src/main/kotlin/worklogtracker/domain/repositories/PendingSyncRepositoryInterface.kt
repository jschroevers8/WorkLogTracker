package worklogtracker.domain.repositories

import worklogtracker.domain.entities.PendingSyncEntity
import worklogtracker.domain.entities.enums.SyncStatus
import worklogtracker.domain.valueobjects.user.UserId

interface PendingSyncRepositoryInterface {
    suspend fun save(sync: PendingSyncEntity): PendingSyncEntity
    suspend fun findById(id: Int): PendingSyncEntity?
    suspend fun findUnsynced(userId: UserId): List<PendingSyncEntity>
    suspend fun findByStatus(status: SyncStatus): List<PendingSyncEntity>
    suspend fun markAsSynced(id: Int): Boolean
    suspend fun markAsFailed(id: Int, error: String): Boolean
    suspend fun delete(id: Int): Boolean
    suspend fun deleteOlderThan(days: Int): Int
}

