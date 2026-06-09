package worklogtracker.presentation.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val projectId: Int,
    val title: String,
    val description: String? = null,
    val estimatedHours: Double,
    val deadline: String? = null,
    val priority: String
)
