package worklogtracker.application.usecases.notification

import worklogtracker.application.dto.notification.NotificationResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.factories.NotificationFactory
import worklogtracker.domain.repositories.NotificationRepositoryInterface
import worklogtracker.domain.entities.enums.NotificationType
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId

//TODO route voor maken
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

