package worklogtracker.backend.infrastructure.hydrators

import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.v1.core.ResultRow
import worklogtracker.backend.domain.entities.TaskPhotoEntity
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskPhotoId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.TaskPhotoTable

fun ResultRow.hydrateTaskPhoto() =
    TaskPhotoEntity(
        id = TaskPhotoId(this[TaskPhotoTable.id]),
        taskId = TaskId(this[TaskPhotoTable.taskId]),
        photoUrl = this[TaskPhotoTable.photoUrl],
        uploadedBy = UserId(this[TaskPhotoTable.uploadedBy]),
        uploadedAt = this[TaskPhotoTable.uploadedAt].toJavaLocalDateTime(),
    )
