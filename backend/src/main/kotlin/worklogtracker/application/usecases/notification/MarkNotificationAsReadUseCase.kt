package worklogtracker.application.usecases.notification

import worklogtracker.domain.exceptions.NotificationNotFoundException
import worklogtracker.domain.repositories.NotificationRepositoryInterface
import worklogtracker.domain.valueobjects.notification.NotificationId
import worklogtracker.domain.valueobjects.user.UserId

class MarkNotificationAsReadUseCase(
    private val notificationRepository: NotificationRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId,
        notificationId: NotificationId
    ): Boolean {
        val notification = notificationRepository.findById(notificationId) 
            ?: throw NotificationNotFoundException(notificationId.value.toString())
        
        if (notification.userId != userId) {
            throw Exception("You don't own this notification")
        }
        
        return notificationRepository.markAsRead(notificationId)
    }
}

