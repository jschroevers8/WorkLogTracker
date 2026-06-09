package worklogtracker.presentation.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class StartTimerRequest(
    val taskId: Int
)
