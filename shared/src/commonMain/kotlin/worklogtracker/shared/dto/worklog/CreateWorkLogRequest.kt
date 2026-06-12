package worklogtracker.shared.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class CreateWorkLogRequest(
    val taskAssignmentId: Int,
    val hours: Double,
    val description: String? = null
)
