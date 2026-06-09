package worklogtracker.domain.repositories

import worklogtracker.domain.entities.AttachmentEntity
import worklogtracker.domain.valueobjects.attachment.AttachmentId
import worklogtracker.domain.valueobjects.task.TaskId

interface AttachmentRepositoryInterface {
    suspend fun findById(id: AttachmentId): AttachmentEntity?
    suspend fun save(attachment: AttachmentEntity): AttachmentEntity
    suspend fun delete(id: AttachmentId): Boolean
    suspend fun findByTask(taskId: TaskId): List<AttachmentEntity>
}

