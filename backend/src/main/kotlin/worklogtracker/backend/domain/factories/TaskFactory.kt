package worklogtracker.backend.domain.factories

import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class TaskFactory {
    
    fun create(
        projectId: ProjectId,
        createdBy: UserId,
        title: String,
        description: String? = null
    ): TaskEntity {
        return TaskEntity(
            projectId = projectId,
            title = title,
            description = description,
            status = TaskStatus.OPEN,
            createdBy = createdBy,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

