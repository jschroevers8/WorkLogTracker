package worklogtracker.shared.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class WorkLogResponse(
    val id: Int?,
    val taskAssignmentId: Int,
    val userId: Int,
    val hours: Double,
    val description: String?,
    val createdAt: String
)
