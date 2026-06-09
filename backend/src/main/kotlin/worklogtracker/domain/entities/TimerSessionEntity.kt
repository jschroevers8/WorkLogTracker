package worklogtracker.domain.entities

import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.timer.TimerSessionId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.valueobjects.worklog.WorkLogId
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * TimerSession Aggregate Root
 * 
 * Represents an active or completed timer session.
 * Convertible to WorkLog after completion.
 * 
 * Domain Rules:
 * - Only one active timer per user at a time
 * - EndedAt must be after StartedAt (when completed)
 * - IsRunning determines if timer is still active
 */
data class TimerSessionEntity(
    val id: TimerSessionId? = null,
    val taskId: TaskId,
    val userId: UserId,
    val startedAt: LocalDateTime,
    val endedAt: LocalDateTime? = null,
    val isRunning: Boolean = true,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    
    init {
        if (!isRunning && endedAt != null) {
            require(endedAt > startedAt) { "EndedAt must be after StartedAt" }
        }
    }

    /**
     * Stop the timer (mark as ended)
     */
    fun stop(stoppedAt: LocalDateTime = LocalDateTime.now()): TimerSessionEntity {
        require(isRunning) { "Timer is already stopped" }
        return this.copy(
            isRunning = false,
            endedAt = stoppedAt,
            updatedAt = LocalDateTime.now()
        )
    }

    /**
     * Calculate elapsed duration in minutes
     */
    fun calculateElapsedMinutes(): Long {
        val end = endedAt ?: LocalDateTime.now()
        return ChronoUnit.MINUTES.between(startedAt, end)
    }

    /**
     * Convert to WorkLog entity
     */
    fun toWorkLog(workLogId: Int): WorkLogEntity {
        return WorkLogEntity(
            id = WorkLogId(workLogId),
            taskId = taskId,
            userId = userId,
            startTime = startedAt,
            endTime = endedAt,
            durationMinutes = calculateElapsedMinutes().toInt(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

