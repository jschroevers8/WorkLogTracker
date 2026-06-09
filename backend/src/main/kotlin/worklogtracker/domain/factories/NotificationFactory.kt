package worklogtracker.domain.factories

import worklogtracker.domain.entities.NotificationEntity
import worklogtracker.domain.entities.enums.NotificationType
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class NotificationFactory {
    
    fun create(
        userId: UserId,
        title: String,
        message: String,
        type: NotificationType,
        taskId: TaskId? = null
    ): NotificationEntity {
        return NotificationEntity(
            userId = userId,
            taskId = taskId,
            title = title,
            message = message,
            type = type,
            sentAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now()
        )
    }
}

