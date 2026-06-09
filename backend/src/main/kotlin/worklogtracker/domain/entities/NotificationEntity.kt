package worklogtracker.domain.entities

import worklogtracker.domain.entities.enums.NotificationType
import worklogtracker.domain.valueobjects.notification.NotificationId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

data class NotificationEntity(
    val id: NotificationId? = null,
    val userId: UserId,
    val taskId: TaskId?,
    val title: String,
    val message: String,
    val type: NotificationType,
    val sentAt: LocalDateTime,
    val isRead: Boolean = false,
    val createdAt: LocalDateTime
) {
    
    /**
     * Mark notification as read
     */
    fun markAsRead(): NotificationEntity = this.copy(isRead = true)

    /**
     * Create notification for deadline warning
     */
    companion object {
        fun createDeadlineWarning(
            userId: UserId,
            taskId: TaskId,
            hoursUntilDeadline: Int
        ): NotificationEntity {
            return NotificationEntity(
                userId = userId,
                taskId = taskId,
                title = "Task Deadline Warning",
                message = "Task is due in $hoursUntilDeadline hours",
                type = NotificationType.DEADLINE_WARNING,
                sentAt = LocalDateTime.now(),
                createdAt = LocalDateTime.now()
            )
        }

        fun createTaskAssigned(userId: UserId, taskId: TaskId, taskTitle: String): NotificationEntity {
            return NotificationEntity(
                userId = userId,
                taskId = taskId,
                title = "New Task Assigned",
                message = "You have been assigned to: $taskTitle",
                type = NotificationType.TASK_ASSIGNED,
                sentAt = LocalDateTime.now(),
                createdAt = LocalDateTime.now()
            )
        }
    }
}

