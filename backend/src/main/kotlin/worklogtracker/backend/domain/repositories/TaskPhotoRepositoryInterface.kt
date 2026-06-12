package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.TaskPhotoEntity
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskPhotoId

interface TaskPhotoRepositoryInterface {
    suspend fun findById(id: TaskPhotoId): TaskPhotoEntity?
    suspend fun save(photo: TaskPhotoEntity): TaskPhotoEntity
    suspend fun findByTask(taskId: TaskId): List<TaskPhotoEntity>
}
