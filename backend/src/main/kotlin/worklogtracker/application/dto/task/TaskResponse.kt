package worklogtracker.application.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: Int?,
    val projectId: Int,
    val assignedUserId: Int?,
    val title: String,
    val description: String?,
    val estimatedHours: Double,
    val actualHours: Double,
    val deadline: String?,
    val priority: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
