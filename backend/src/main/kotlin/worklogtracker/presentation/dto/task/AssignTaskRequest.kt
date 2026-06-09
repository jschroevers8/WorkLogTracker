package worklogtracker.presentation.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class AssignTaskRequest(
    val assignedUserId: Int
)
