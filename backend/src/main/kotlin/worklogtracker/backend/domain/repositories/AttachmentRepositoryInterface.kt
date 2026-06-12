package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.AttachmentEntity
import worklogtracker.backend.domain.valueobjects.attachment.AttachmentId
import worklogtracker.backend.domain.valueobjects.task.TaskId

interface AttachmentRepositoryInterface {
    suspend fun findById(id: AttachmentId): AttachmentEntity?
    suspend fun save(attachment: AttachmentEntity): AttachmentEntity
    suspend fun delete(id: AttachmentId): Boolean
    suspend fun findByTask(taskId: TaskId): List<AttachmentEntity>
}

