package worklogtracker.presentation.dto.sync
import kotlinx.serialization.Serializable
@Serializable
data class SyncItem(
    val id: String,
    val entityType: String,
    val entityId: String,
    val operation: String,
    val payload: String
)
