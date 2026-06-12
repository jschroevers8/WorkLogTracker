package worklogtracker.backend.domain.entities

import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.exceptions.InvalidTaskStatusTransitionException
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

data class TaskEntity(
    val id: TaskId? = null,
    val projectId: ProjectId,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val createdBy: UserId,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    
    fun updateStatus(newStatus: TaskStatus): TaskEntity {
        val validTransitions = mapOf(
            TaskStatus.OPEN to setOf(TaskStatus.IN_PROGRESS, TaskStatus.CANCELLED),
            TaskStatus.IN_PROGRESS to setOf(TaskStatus.COMPLETED, TaskStatus.CANCELLED),
            TaskStatus.COMPLETED to setOf(),
            TaskStatus.CANCELLED to setOf()
        )

        val allowed = validTransitions[status] ?: emptySet()
        if (newStatus !in allowed) {
            throw InvalidTaskStatusTransitionException(
                "Cannot transition from $status to $newStatus"
            )
        }

        return this.copy(status = newStatus, updatedAt = LocalDateTime.now())
    }

    fun complete(): TaskEntity = updateStatus(TaskStatus.COMPLETED)

    fun cancel(): TaskEntity = updateStatus(TaskStatus.CANCELLED)
}

