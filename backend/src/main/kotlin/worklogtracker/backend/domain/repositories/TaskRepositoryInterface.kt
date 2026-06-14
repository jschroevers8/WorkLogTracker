package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId

interface TaskRepositoryInterface {
    suspend fun findById(id: TaskId): TaskEntity?

    suspend fun save(task: TaskEntity): TaskEntity

    suspend fun update(task: TaskEntity): Boolean

    suspend fun delete(id: TaskId): Boolean

    suspend fun findByProject(projectId: ProjectId): List<TaskEntity>

    suspend fun findByUser(userId: UserId): List<TaskEntity>

    suspend fun findByStatus(status: TaskStatus): List<TaskEntity>
}
