package worklogtracker.application.usecases.sync

import worklogtracker.domain.repositories.PendingSyncRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.entities.PendingSyncEntity
import worklogtracker.domain.entities.enums.SyncStatus
import java.time.LocalDateTime
import java.util.UUID

//TODO
class AddPendingSyncUseCase(
    private val syncRepository: PendingSyncRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId,
        entityType: String,
        entityId: UUID,
        operation: String,
        payload: String
    ) {
        val pendingSync = PendingSyncEntity(
            userId = userId,
            entityType = entityType,
            entityId = entityId,
            operation = operation,
            payload = payload,
            syncStatus = SyncStatus.PENDING,
            createdAt = LocalDateTime.now()
        )
        
        syncRepository.save(pendingSync)
    }
}

