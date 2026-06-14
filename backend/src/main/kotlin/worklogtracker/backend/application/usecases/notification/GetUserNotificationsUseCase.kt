package worklogtracker.backend.application.usecases.notification

import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.repositories.NotificationRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.shared.dto.notification.NotificationResponse

class GetUserNotificationsUseCase(
    private val notificationRepository: NotificationRepositoryInterface,
) {
    suspend operator fun invoke(
        userId: UserId,
        unreadOnly: Boolean = false,
    ): List<NotificationResponse> = notificationRepository.findByUser(userId, unreadOnly).map { it.toResponse() }
}
