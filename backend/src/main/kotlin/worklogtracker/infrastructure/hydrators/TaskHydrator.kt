package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.TaskEntity
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.tables.TaskTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateTask() =
    TaskEntity(
        id = TaskId(this[TaskTable.id]),
        projectId = ProjectId(this[TaskTable.projectId]),
        assignedUserId = this[TaskTable.assignedUserId]?.let { UserId(it) },
        title = this[TaskTable.title],
        description = this[TaskTable.description],
        estimatedHours = this[TaskTable.estimatedHours],
        actualHours = this[TaskTable.actualHours],
        deadline = this[TaskTable.deadline]?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        },
        priority = this[TaskTable.priority], // assuming enum mapping elsewhere
        status = TaskStatus.valueOf(this[TaskTable.status]),
        createdAt = Instant.ofEpochMilli(this[TaskTable.createdAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        updatedAt = Instant.ofEpochMilli(this[TaskTable.updatedAt])
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    )