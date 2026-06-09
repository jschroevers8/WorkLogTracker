package worklogtracker.application.mappers

import worklogtracker.application.dto.notification.NotificationResponse
import worklogtracker.domain.entities.NotificationEntity
import java.time.format.DateTimeFormatter

fun NotificationEntity.toResponse() =
    NotificationResponse(
        id = this.id?.value,
        userId = this.userId.value,
        taskId = this.taskId?.value,
        title = this.title,
        message = this.message,
        type = this.type.name,
        sentAt = this.sentAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        isRead = this.isRead,
        createdAt = this.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )
