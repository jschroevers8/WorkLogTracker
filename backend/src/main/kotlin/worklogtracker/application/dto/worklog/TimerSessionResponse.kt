package worklogtracker.application.dto.worklog

import kotlinx.serialization.Serializable

@Serializable
data class TimerSessionResponse(
    val id: Int,
    val taskId: Int,
    val userId: Int,
    val startedAt: String,
    val endedAt: String?,
    val isRunning: Boolean,
    val elapsedMinutes: Long? = null
)
