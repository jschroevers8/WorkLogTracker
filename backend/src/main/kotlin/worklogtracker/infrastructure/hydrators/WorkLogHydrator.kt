package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.WorkLogEntity
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.valueobjects.worklog.WorkLogId
import worklogtracker.infrastructure.tables.WorkLogTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateWorkLog() =
    WorkLogEntity(
        id = WorkLogId(this[WorkLogTable.id]),
        taskId = TaskId(this[WorkLogTable.taskId]),
        userId = UserId(this[WorkLogTable.userId]),
        startTime = Instant.ofEpochMilli(this[WorkLogTable.startTime])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        endTime = this[WorkLogTable.endTime]?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        },
        durationMinutes = this[WorkLogTable.durationMinutes],
        notes = this[WorkLogTable.notes],
        isSynced = this[WorkLogTable.isSynced],
        createdAt = Instant.ofEpochMilli(this[WorkLogTable.createdAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        updatedAt = Instant.ofEpochMilli(this[WorkLogTable.updatedAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    )