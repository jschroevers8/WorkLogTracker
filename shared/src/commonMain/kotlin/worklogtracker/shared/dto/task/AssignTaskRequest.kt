package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class AssignTaskRequest(
    val assignedUserId: Int,
)
