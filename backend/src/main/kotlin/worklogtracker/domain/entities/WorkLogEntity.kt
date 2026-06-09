package worklogtracker.domain.entities

import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.valueobjects.worklog.WorkLogId
import java.time.LocalDateTime

/**
 * WorkLog Aggregate Root
 * 
 * Represents logged time against a task.
 * Inherits from concept of "Rental" and "RentalTrip" (time-based tracking).
 * 
 * Domain Rules:
 * - EndTime must be after StartTime
 * - WorkLog cannot record time in the future
 * - Only user assigned to task can create work log
 */
data class WorkLogEntity(
    val id: WorkLogId? = null,
    val taskId: TaskId,
    val userId: UserId,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val durationMinutes: Int?,
    val notes: String? = null,
    val isSynced: Boolean = false,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    
    init {
        if (endTime != null) {
            require(endTime > startTime) { "EndTime must be after StartTime" }
            require(endTime <= LocalDateTime.now()) { "Cannot record future time" }
        }
    }

    /**
     * Calculate duration from start and end times
     */
    fun calculateDuration(): Int? {
        return if (endTime != null) {
            ((endTime.toLocalTime().toNanoOfDay() - startTime.toLocalTime().toNanoOfDay()) / 60_000_000_000L).toInt()
        } else {
            null
        }
    }

    /**
     * Mark work log as synced
     */
    fun markAsSynced(): WorkLogEntity = this.copy(isSynced = true, updatedAt = LocalDateTime.now())

    /**
     * Check if work log is complete (has end time)
     */
    fun isComplete(): Boolean = endTime != null
}

