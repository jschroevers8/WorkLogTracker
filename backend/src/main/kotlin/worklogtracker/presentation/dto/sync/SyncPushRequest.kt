package worklogtracker.presentation.dto.sync
import kotlinx.serialization.Serializable
@Serializable
data class SyncPushRequest(
    val items: List<SyncItem>
)
