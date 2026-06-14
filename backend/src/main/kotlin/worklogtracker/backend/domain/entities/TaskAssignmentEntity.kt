package worklogtracker.backend.domain.entities

import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

data class TaskAssignmentEntity(
    val id: TaskAssignmentId? = null,
    val taskId: TaskId,
    val userId: UserId,
    val assignedAt: LocalDateTime,
    val status: TaskStatus,
)
