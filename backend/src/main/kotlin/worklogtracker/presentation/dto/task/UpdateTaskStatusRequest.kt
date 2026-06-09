package worklogtracker.presentation.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskStatusRequest(
    val status: String
)
