package worklogtracker.backend.application.usecases.notification

import worklogtracker.shared.dto.notification.NotificationResponse
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.factories.NotificationFactory
import worklogtracker.backend.domain.repositories.NotificationRepositoryInterface
import worklogtracker.backend.domain.entities.enums.NotificationType
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId

class CreateNotificationUseCase(
    private val notificationRepository: NotificationRepositoryInterface,
    private val notificationFactory: NotificationFactory
) {
    
    suspend operator fun invoke(
        userId: UserId,
        title: String,
        message: String,
        type: NotificationType,
        taskId: TaskId? = null
    ): NotificationResponse {
        val notification = notificationFactory.create(
            userId = userId,
            title = title,
            message = message,
            type = type,
            taskId = taskId
        )
        
        return notificationRepository.save(notification).toResponse()
    }
}

