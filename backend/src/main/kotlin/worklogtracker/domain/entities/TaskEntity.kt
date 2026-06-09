package worklogtracker.domain.entities

import worklogtracker.domain.entities.enums.Priority
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.exceptions.InvalidTaskStatusTransitionException
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Task Aggregate Root
 * 
 * Represents a unit of work within a project.
 * Inherits from concept of "Car" (assignable resource) and "Advertisement" (availability definition).
 * 
 * Domain Rules:
 * - EstimatedHours must be positive
 * - Deadline must be in the future
 * - Only assigned user can log time against task
 * - Status transitions follow defined state machine
 */
data class TaskEntity(
    val id: TaskId? = null,
    val projectId: ProjectId,
    val assignedUserId: UserId?,
    val title: String,
    val description: String?,
    val estimatedHours: BigDecimal,
    val actualHours: BigDecimal = BigDecimal.ZERO,
    val deadline: LocalDateTime?,
    val priority: Priority,
    val status: TaskStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    
    init {
        require(estimatedHours > BigDecimal.ZERO) { "EstimatedHours must be positive" }
        if (deadline != null) {
            require(deadline > LocalDateTime.now()) { "Deadline must be in the future" }
        }
    }

    /**
     * Assign task to a user
     */
    fun assignTo(userId: UserId): TaskEntity {
        require(status == TaskStatus.OPEN) { "Cannot assign task that is not OPEN" }
        return this.copy(assignedUserId = userId, updatedAt = LocalDateTime.now())
    }

    /**
     * Update task status with validation
     */
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

    /**
     * Check if task can accept work logs
     */
    fun canLogTime(): Boolean = status == TaskStatus.IN_PROGRESS

    /**
     * Check if task is overdue
     */
    fun isOverdue(): Boolean = deadline != null && deadline < LocalDateTime.now() && 
                               status !in listOf(TaskStatus.COMPLETED, TaskStatus.CANCELLED)

    /**
     * Mark task as completed (end state)
     */
    fun complete(): TaskEntity = updateStatus(TaskStatus.COMPLETED)

    /**
     * Mark task as cancelled (terminal state)
     */
    fun cancel(): TaskEntity = updateStatus(TaskStatus.CANCELLED)
}

