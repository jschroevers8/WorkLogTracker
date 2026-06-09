package worklogtracker.shared.dto.sync
import kotlinx.serialization.Serializable
@Serializable
data class SyncResult(
    val id: String,
    val status: String,
    val error: String? = null
)
