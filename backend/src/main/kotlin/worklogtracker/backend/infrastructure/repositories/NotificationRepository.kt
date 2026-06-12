package worklogtracker.backend.infrastructure.repositories

import worklogtracker.backend.domain.entities.NotificationEntity
import worklogtracker.backend.domain.repositories.NotificationRepositoryInterface
import worklogtracker.backend.domain.valueobjects.notification.NotificationId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.hydrators.hydrateNotification
import worklogtracker.backend.infrastructure.tables.NotificationTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

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
                it[sentAt] =
                    Instant.from(notification.sentAt.atZone(ZoneId.systemDefault())).toEpochMilli()
                it[isRead] = notification.isRead
                it[createdAt] =
                    Instant.from(notification.createdAt.atZone(ZoneId.systemDefault())).toEpochMilli()
            }
        }

        return notification
    }

    override suspend fun update(notification: NotificationEntity): Boolean =
        transaction {
            NotificationTable.update(
                { NotificationTable.id eq notification.id!!.value }
            ) {
                it[isRead] = notification.isRead
            } > 0
        }

    override suspend fun markAsRead(id: NotificationId): Boolean =
        transaction {
            NotificationTable.update(
                { NotificationTable.id eq id.value }
            ) {
                it[isRead] = true
            } > 0
        }

    override suspend fun findByUser(
        userId: UserId,
        unreadOnly: Boolean
    ): List<NotificationEntity> =
        transaction {
            if (unreadOnly) {
                NotificationTable
                    .selectAll()
                    .where {
                        (NotificationTable.userId eq userId.value) and
                                (NotificationTable.isRead eq false)
                    }
                    .map { it.hydrateNotification() }
            } else {
                NotificationTable
                    .selectAll()
                    .where { NotificationTable.userId eq userId.value }
                    .map { it.hydrateNotification() }
            }
        }

    override suspend fun deleteOlderThan(days: Int): Int =
        transaction {
            val cutoffDate =
                System.currentTimeMillis() - (days * 24L * 60L * 60L * 1000L)

            NotificationTable.deleteWhere {
                NotificationTable.createdAt lessEq cutoffDate
            }
        }
}