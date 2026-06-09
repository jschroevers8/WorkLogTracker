package worklogtracker.presentation.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class StopTimerRequest(
    val sessionId: Int,
    val notes: String? = null
)
