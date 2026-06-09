package worklogtracker.shared.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class CreateWorkLogRequest(
    val taskId: Int,
    val startTime: String,
    val endTime: String,
    val notes: String? = null,
    val photoBase64: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)
