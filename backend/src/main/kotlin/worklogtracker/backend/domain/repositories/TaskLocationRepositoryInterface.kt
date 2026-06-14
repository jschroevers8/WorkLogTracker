package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.TaskLocationEntity
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskLocationId

interface TaskLocationRepositoryInterface {
    suspend fun findById(id: TaskLocationId): TaskLocationEntity?

    suspend fun save(location: TaskLocationEntity): TaskLocationEntity

    suspend fun findByTask(taskId: TaskId): List<TaskLocationEntity>
}
