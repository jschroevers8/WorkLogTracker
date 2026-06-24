package worklogtracker.backend.infrastructure.repositories

import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import worklogtracker.backend.domain.entities.NotificationEntity
import worklogtracker.backend.domain.repositories.NotificationRepositoryInterface
import worklogtracker.backend.domain.valueobjects.notification.NotificationId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.hydrators.hydrateNotification
import worklogtracker.backend.infrastructure.tables.NotificationTable

class NotificationRepository : NotificationRepositoryInterface {
    override suspend fun findById(id: NotificationId): NotificationEntity? =
        transaction {
            NotificationTable
                .selectAll()
                .where { NotificationTable.id eq id.value }
                .map { it.hydrateNotification() }
                .singleOrNull()
        }

    override suspend fun save(notification: NotificationEntity): NotificationEntity {
        transaction {
            NotificationTable.insert {
                it[userId] = notification.userId.value
                it[taskId] = notification.taskId?.value
                it[title] = notification.title
                it[message] = notification.message
                it[type] = notification.type.name
                it[sentAt] = notification.sentAt.toKotlinLocalDateTime()
                it[isRead] = notification.isRead
                it[createdAt] = notification.createdAt.toKotlinLocalDateTime()
            }
        }

        return notification
    }

    override suspend fun update(notification: NotificationEntity): Boolean =
        transaction {
            NotificationTable.update(
                { NotificationTable.id eq notification.id!!.value },
            ) {
                it[isRead] = notification.isRead
            } > 0
        }

    override suspend fun markAsRead(id: NotificationId): Boolean =
        transaction {
            NotificationTable.update(
                { NotificationTable.id eq id.value },
            ) {
                it[isRead] = true
            } > 0
        }

    override suspend fun findByUser(
        userId: UserId,
        unreadOnly: Boolean,
    ): List<NotificationEntity> =
        transaction {
            if (unreadOnly) {
                NotificationTable
                    .selectAll()
                    .where {
                        (NotificationTable.userId eq userId.value) and
                            (NotificationTable.isRead eq false)
                    }.map { it.hydrateNotification() }
            } else {
                NotificationTable
                    .selectAll()
                    .where { NotificationTable.userId eq userId.value }
                    .map { it.hydrateNotification() }
            }
        }
}
