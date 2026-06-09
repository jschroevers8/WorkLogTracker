package worklogtracker.domain.entities

import worklogtracker.domain.valueobjects.attachment.AttachmentId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

data class AttachmentEntity(
    val id: AttachmentId? = null,
    val taskId: TaskId,
    val fileName: String,
    val filePath: String,
    val fileSize: Int,
    val uploadedByUserId: UserId,
    val uploadedAt: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    /**
     * Check if file size is within limits (10MB)
     */
    fun isWithinSizeLimit(): Boolean = fileSize <= 10 * 1024 * 1024

    /**
     * Get file extension
     */
    fun getExtension(): String = fileName.substringAfterLast('.', "")
}

