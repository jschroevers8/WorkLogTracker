package worklogtracker.domain.factories

import worklogtracker.domain.entities.WorkLogEntity
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WorkLogFactory {
    
    fun create(
        taskId: TaskId,
        userId: UserId,
        startTime: LocalDateTime,
        endTime: LocalDateTime? = null,
        notes: String? = null
    ): WorkLogEntity {
        val durationMinutes = if (endTime != null) {
            ChronoUnit.MINUTES.between(startTime, endTime).toInt()
        } else {
            null
        }
        
        return WorkLogEntity(
            taskId = taskId,
            userId = userId,
            startTime = startTime,
            endTime = endTime,
            durationMinutes = durationMinutes,
            notes = notes,
            isSynced = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

