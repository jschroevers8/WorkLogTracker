package worklogtracker.backend.application.mappers

import worklogtracker.shared.dto.notification.NotificationResponse
import worklogtracker.backend.domain.entities.NotificationEntity
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
