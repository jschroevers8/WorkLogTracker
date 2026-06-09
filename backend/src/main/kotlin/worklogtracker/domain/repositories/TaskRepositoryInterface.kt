package worklogtracker.domain.repositories

import worklogtracker.domain.entities.TaskEntity
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId

interface TaskRepositoryInterface {
    suspend fun findById(id: TaskId): TaskEntity?
    suspend fun save(task: TaskEntity): TaskEntity
    suspend fun update(task: TaskEntity): Boolean
    suspend fun delete(id: TaskId): Boolean
    suspend fun findByProject(projectId: ProjectId): List<TaskEntity>
    suspend fun findByUser(userId: UserId): List<TaskEntity>
    suspend fun findByStatus(status: TaskStatus): List<TaskEntity>
    suspend fun findUpcomingDeadlines(daysAhead: Int): List<TaskEntity>
}

