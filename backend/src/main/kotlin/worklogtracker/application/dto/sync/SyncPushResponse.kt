package worklogtracker.application.dto.sync
import kotlinx.serialization.Serializable
@Serializable
data class SyncPushResponse(
    val results: List<SyncResult>
)
