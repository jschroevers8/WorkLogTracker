package worklogtracker.backend.infrastructure.hydrators

import org.jetbrains.exposed.sql.ResultRow
import worklogtracker.backend.domain.entities.TimeEntryEntity
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.domain.valueobjects.worklog.TimeEntryId
import worklogtracker.backend.infrastructure.tables.TimeEntryTable
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateTimeEntry() = TimeEntryEntity(
    id = TimeEntryId(this[TimeEntryTable.id]),
    taskAssignmentId = TaskAssignmentId(this[TimeEntryTable.taskAssignmentId]),
    userId = UserId(this[TimeEntryTable.userId]),
    hours = this[TimeEntryTable.hours],
    description = this[TimeEntryTable.description],
    aiDescription = this[TimeEntryTable.aiDescription],
    createdAt = Instant.ofEpochMilli(this[TimeEntryTable.createdAt])
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
)
