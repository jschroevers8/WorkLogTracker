package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: Int?,
    val projectId: Int,
    val title: String,
    val description: String?,
    val status: String,
    val createdBy: Int,
    val createdAt: String,
    val updatedAt: String,
    val assignmentId: Int? = null,
    val photoUrls: List<String> = emptyList(),
    val locations: List<TaskLocationDto> = emptyList(),
    val totalHours: Double = 0.0
)

@Serializable
data class TaskLocationDto(
    val latitude: Double,
    val longitude: Double,
    val recordedAt: String
)
