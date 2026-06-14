package worklogtracker.backend.application.usecases.notification

import worklogtracker.backend.domain.exceptions.NotificationNotFoundException
import worklogtracker.backend.domain.repositories.NotificationRepositoryInterface
import worklogtracker.backend.domain.valueobjects.notification.NotificationId
import worklogtracker.backend.domain.valueobjects.user.UserId

class MarkNotificationAsReadUseCase(
    private val notificationRepository: NotificationRepositoryInterface,
) {
    suspend operator fun invoke(
        userId: UserId,
        notificationId: NotificationId,
    ): Boolean {
        val notification =
            notificationRepository.findById(notificationId)
                ?: throw NotificationNotFoundException(notificationId.value.toString())

        if (notification.userId != userId) {
            throw Exception("You don't own this notification")
        }

        return notificationRepository.markAsRead(notificationId)
    }
}
