package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskRequest(
    val title: String? = null,
    val description: String? = null,
    val estimatedHours: Double? = null,
    val deadline: String? = null,
    val priority: String? = null
)
