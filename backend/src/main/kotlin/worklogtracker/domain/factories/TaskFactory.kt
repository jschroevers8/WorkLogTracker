package worklogtracker.domain.factories

import worklogtracker.domain.entities.TaskEntity
import worklogtracker.domain.entities.enums.Priority
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import java.math.BigDecimal
import java.time.LocalDateTime

class TaskFactory {
    
    fun create(
        projectId: ProjectId,
        title: String,
        description: String? = null,
        estimatedHours: BigDecimal,
        deadline: LocalDateTime? = null,
        priority: Priority = Priority.MEDIUM
    ): TaskEntity {
        return TaskEntity(
            projectId = projectId,
            assignedUserId = null,
            title = title,
            description = description,
            estimatedHours = estimatedHours,
            deadline = deadline,
            priority = priority,
            status = TaskStatus.OPEN,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

