package worklogtracker.application.usecases.sync

import worklogtracker.domain.repositories.PendingSyncRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

//TODO
class GetPendingSyncsUseCase(
    private val syncRepository: PendingSyncRepositoryInterface
) {
    
    suspend operator fun invoke(userId: UserId): List<PendingSyncResponse> {
        val pendingSyncs = syncRepository.findUnsynced(userId)
        
        return pendingSyncs.map { sync ->
            PendingSyncResponse(
                id = sync.id.toString(),
                entityType = sync.entityType,
                entityId = sync.entityId.toString(),
                operation = sync.operation,
                payload = sync.payload,
                createdAt = sync.createdAt
            )
        }
    }
}

data class PendingSyncResponse(
    val id: String,
    val entityType: String,
    val entityId: String,
    val operation: String,
    val payload: String,
    val createdAt: LocalDateTime
)

