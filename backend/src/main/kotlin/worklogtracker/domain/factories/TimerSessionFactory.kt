package worklogtracker.domain.factories

import worklogtracker.domain.entities.TimerSessionEntity
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class TimerSessionFactory {
    
    fun create(
        taskId: TaskId,
        userId: UserId,
        startedAt: LocalDateTime = LocalDateTime.now()
    ): TimerSessionEntity {
        return TimerSessionEntity(
            taskId = taskId,
            userId = userId,
            startedAt = startedAt,
            isRunning = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

