package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.TaskAssignmentEntity
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId

interface TaskAssignmentRepositoryInterface {
    suspend fun findById(id: TaskAssignmentId): TaskAssignmentEntity?

    suspend fun save(assignment: TaskAssignmentEntity): TaskAssignmentEntity

    suspend fun update(assignment: TaskAssignmentEntity): Boolean

    suspend fun findByTask(taskId: TaskId): List<TaskAssignmentEntity>

    suspend fun findByUser(userId: UserId): List<TaskAssignmentEntity>
}
