package worklogtracker.application.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class WorkLogResponse(
    val id: Int?,
    val taskId: Int,
    val userId: Int,
    val startTime: String,
    val endTime: String?,
    val durationMinutes: Int?,
    val notes: String?,
    val isSynced: Boolean,
    val createdAt: String,
    val updatedAt: String
)
