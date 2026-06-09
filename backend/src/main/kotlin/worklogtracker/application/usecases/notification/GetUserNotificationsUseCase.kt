package worklogtracker.application.usecases.notification

import worklogtracker.application.dto.notification.NotificationResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.repositories.NotificationRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId

class GetUserNotificationsUseCase(
    private val notificationRepository: NotificationRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId,
        unreadOnly: Boolean = false
    ): List<NotificationResponse> {
        return notificationRepository.findByUser(userId, unreadOnly).map { it.toResponse() }
    }
}
