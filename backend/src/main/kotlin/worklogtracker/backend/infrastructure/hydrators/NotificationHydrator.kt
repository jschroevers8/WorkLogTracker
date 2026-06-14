package worklogtracker.backend.infrastructure.hydrators

import worklogtracker.backend.domain.entities.NotificationEntity
import worklogtracker.backend.domain.entities.enums.NotificationType
import worklogtracker.backend.domain.valueobjects.notification.NotificationId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.NotificationTable
import org.jetbrains.exposed.sql.ResultRow
import kotlinx.datetime.toJavaLocalDateTime

fun ResultRow.hydrateNotification() =
    NotificationEntity(
        id = NotificationId(this[NotificationTable.id]),
        userId = UserId(this[NotificationTable.userId]),
        taskId = this[NotificationTable.taskId]?.let { TaskId(it) },
        title = this[NotificationTable.title],
        message = this[NotificationTable.message],
        type = NotificationType.valueOf(this[NotificationTable.type]),
        sentAt = this[NotificationTable.sentAt].toJavaLocalDateTime(),
        isRead = this[NotificationTable.isRead],
        createdAt = this[NotificationTable.createdAt].toJavaLocalDateTime()
    )