package worklogtracker.backend.domain.entities

import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskPhotoId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

data class TaskPhotoEntity(
    val id: TaskPhotoId? = null,
    val taskId: TaskId,
    val photoUrl: String,
    val uploadedBy: UserId,
    val uploadedAt: LocalDateTime,
)
