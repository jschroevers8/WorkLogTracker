package worklogtracker.domain.repositories

import worklogtracker.domain.entities.NotificationEntity
import worklogtracker.domain.valueobjects.notification.NotificationId
import worklogtracker.domain.valueobjects.user.UserId

interface NotificationRepositoryInterface {
    suspend fun findById(id: NotificationId): NotificationEntity?
    suspend fun save(notification: NotificationEntity): NotificationEntity
    suspend fun update(notification: NotificationEntity): Boolean
    suspend fun markAsRead(id: NotificationId): Boolean
    suspend fun findByUser(userId: UserId, unreadOnly: Boolean = false): List<NotificationEntity>
    suspend fun deleteOlderThan(days: Int): Int
}

