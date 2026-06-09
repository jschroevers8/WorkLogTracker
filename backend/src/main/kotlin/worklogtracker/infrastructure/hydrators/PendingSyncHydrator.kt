package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.PendingSyncEntity
import worklogtracker.domain.entities.enums.SyncStatus
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.tables.PendingSyncTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

fun ResultRow.hydratePendingSync() =
    PendingSyncEntity(
        id = this[PendingSyncTable.id],
        userId = UserId(this[PendingSyncTable.userId]),
        entityType = this[PendingSyncTable.entityType],
        entityId = UUID.fromString(this[PendingSyncTable.entityId]),
        operation = this[PendingSyncTable.operation],
        payload = this[PendingSyncTable.payload],
        syncStatus = SyncStatus.valueOf(this[PendingSyncTable.syncStatus]),
        syncError = this[PendingSyncTable.syncError],
        createdAt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this[PendingSyncTable.createdAt]),
            ZoneId.systemDefault()
        ),
        syncedAt = this[PendingSyncTable.syncedAt]?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it),
                ZoneId.systemDefault()
            )
        }
    )