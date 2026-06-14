package worklogtracker.backend.infrastructure.hydrators

import worklogtracker.backend.domain.entities.TaskAssignmentEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.TaskAssignmentTable
import org.jetbrains.exposed.sql.ResultRow
import kotlinx.datetime.toJavaLocalDateTime

fun ResultRow.hydrateTaskAssignment() = TaskAssignmentEntity(
    id = TaskAssignmentId(this[TaskAssignmentTable.id]),
    taskId = TaskId(this[TaskAssignmentTable.taskId]),
    userId = UserId(this[TaskAssignmentTable.userId]),
    assignedAt = this[TaskAssignmentTable.assignedAt].toJavaLocalDateTime(),
    status = TaskStatus.valueOf(this[TaskAssignmentTable.status])
)
