package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.NotificationEntity
import worklogtracker.domain.entities.enums.NotificationType
import worklogtracker.domain.valueobjects.notification.NotificationId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.tables.NotificationTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun ResultRow.hydrateNotification() =
    NotificationEntity(
        id = NotificationId(this[NotificationTable.id]),
        userId = UserId(this[NotificationTable.userId]),
        taskId = this[NotificationTable.taskId]?.let { TaskId(it) },
        title = this[NotificationTable.title],
        message = this[NotificationTable.message],
        type = NotificationType.valueOf(this[NotificationTable.type]),
        sentAt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this[NotificationTable.sentAt]),
            ZoneId.systemDefault()
        ),
        isRead = this[NotificationTable.isRead],
        createdAt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this[NotificationTable.createdAt]),
            ZoneId.systemDefault()
        )
    )