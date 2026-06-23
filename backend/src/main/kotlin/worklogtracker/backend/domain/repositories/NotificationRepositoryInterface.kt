package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.NotificationEntity
import worklogtracker.backend.domain.valueobjects.notification.NotificationId
import worklogtracker.backend.domain.valueobjects.user.UserId

interface NotificationRepositoryInterface {
    suspend fun findById(id: NotificationId): NotificationEntity?

    suspend fun save(notification: NotificationEntity): NotificationEntity

    suspend fun update(notification: NotificationEntity): Boolean

    suspend fun markAsRead(id: NotificationId): Boolean

    suspend fun findByUser(
        userId: UserId,
        unreadOnly: Boolean = false,
    ): List<NotificationEntity>
}
