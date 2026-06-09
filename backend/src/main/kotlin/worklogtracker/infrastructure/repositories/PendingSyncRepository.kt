package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.PendingSyncEntity
import worklogtracker.domain.entities.enums.SyncStatus
import worklogtracker.domain.repositories.PendingSyncRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.hydrators.hydratePendingSync
import worklogtracker.infrastructure.tables.PendingSyncTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

class PendingSyncRepository : PendingSyncRepositoryInterface {

    override suspend fun save(sync: PendingSyncEntity): PendingSyncEntity {
        transaction {
            PendingSyncTable.insert {
                it[userId] = sync.userId.value
                it[entityType] = sync.entityType
                it[entityId] = sync.entityId.toString()
                it[operation] = sync.operation
                it[payload] = sync.payload
                it[syncStatus] = sync.syncStatus.name
                it[syncError] = sync.syncError
                it[createdAt] = Instant.now().toEpochMilli()
                it[syncedAt] = sync.syncedAt?.let { date ->
                    Instant.from(date.atZone(ZoneId.systemDefault())).toEpochMilli()
                }
            }
        }

        return sync
    }

    override suspend fun findById(id: Int): PendingSyncEntity? =
        transaction {
            PendingSyncTable
                .selectAll()
                .where { PendingSyncTable.id eq id }
                .map { it.hydratePendingSync() }
                .singleOrNull()
        }

    override suspend fun findUnsynced(userId: UserId): List<PendingSyncEntity> =
        transaction {
            PendingSyncTable
                .selectAll()
                .where {
                    (PendingSyncTable.userId eq userId.value) and
                            (PendingSyncTable.syncStatus eq SyncStatus.PENDING.name)
                }
                .map { it.hydratePendingSync() }
        }

    override suspend fun findByStatus(status: SyncStatus): List<PendingSyncEntity> =
        transaction {
            PendingSyncTable
                .selectAll()
                .where { PendingSyncTable.syncStatus eq status.name }
                .map { it.hydratePendingSync() }
        }

    override suspend fun markAsSynced(id: Int): Boolean =
        transaction {
            PendingSyncTable.update({ PendingSyncTable.id eq id }) {
                it[syncStatus] = SyncStatus.SYNCED.name
                it[syncedAt] = Instant.now().toEpochMilli()
            } > 0
        }

    override suspend fun markAsFailed(id: Int, error: String): Boolean =
        transaction {
            PendingSyncTable.update({ PendingSyncTable.id eq id }) {
                it[syncStatus] = SyncStatus.FAILED.name
                it[syncError] = error
            } > 0
        }

    override suspend fun delete(id: Int): Boolean =
        transaction {
            PendingSyncTable.deleteWhere { PendingSyncTable.id eq id } > 0
        }

    override suspend fun deleteOlderThan(days: Int): Int =
        transaction {
            val cutoff =
                System.currentTimeMillis() - (days * 24L * 60L * 60L * 1000L)

            PendingSyncTable.deleteWhere {
                (PendingSyncTable.syncStatus eq SyncStatus.SYNCED.name) and
                        (PendingSyncTable.syncedAt lessEq cutoff)
            }
        }
}
