package worklogtracker.backend.infrastructure.hydrators

import org.jetbrains.exposed.sql.ResultRow
import kotlinx.datetime.toJavaLocalDateTime
import worklogtracker.backend.domain.entities.TaskLocationEntity
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskLocationId
import worklogtracker.backend.infrastructure.tables.TaskLocationTable

fun ResultRow.hydrateTaskLocation() = TaskLocationEntity(
    id = TaskLocationId(this[TaskLocationTable.id]),
    taskId = TaskId(this[TaskLocationTable.taskId]),
    latitude = this[TaskLocationTable.latitude],
    longitude = this[TaskLocationTable.longitude],
    recordedAt = this[TaskLocationTable.recordedAt].toJavaLocalDateTime()
)
