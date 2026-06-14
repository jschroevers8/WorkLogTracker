package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val projectId: Int,
    val title: String,
    val description: String? = null,
    val assignedUserId: Int,
)
