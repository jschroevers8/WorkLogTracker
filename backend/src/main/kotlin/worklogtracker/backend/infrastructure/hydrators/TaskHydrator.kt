package worklogtracker.backend.infrastructure.hydrators

import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.TaskTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateTask() =
    TaskEntity(
        id = TaskId(this[TaskTable.id]),
        projectId = ProjectId(this[TaskTable.projectId]),
        title = this[TaskTable.title],
        description = this[TaskTable.description],
        status = TaskStatus.valueOf(this[TaskTable.status]),
        createdBy = UserId(this[TaskTable.createdBy]),
        createdAt = Instant.ofEpochMilli(this[TaskTable.createdAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        updatedAt = Instant.ofEpochMilli(this[TaskTable.updatedAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    )