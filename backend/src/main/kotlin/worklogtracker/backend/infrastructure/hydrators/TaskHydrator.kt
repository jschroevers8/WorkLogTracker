package worklogtracker.backend.infrastructure.hydrators

import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.v1.core.ResultRow
import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.TaskTable

fun ResultRow.hydrateTask() =
    TaskEntity(
        id = TaskId(this[TaskTable.id]),
        projectId = ProjectId(this[TaskTable.projectId]),
        title = this[TaskTable.title],
        description = this[TaskTable.description],
        status = TaskStatus.valueOf(this[TaskTable.status]),
        createdBy = UserId(this[TaskTable.createdBy]),
        createdAt = this[TaskTable.createdAt].toJavaLocalDateTime(),
        updatedAt = this[TaskTable.updatedAt].toJavaLocalDateTime(),
    )
