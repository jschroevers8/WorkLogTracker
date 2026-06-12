package worklogtracker.backend.infrastructure.hydrators

import org.jetbrains.exposed.sql.ResultRow
import worklogtracker.backend.domain.entities.TaskPhotoEntity
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskPhotoId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.tables.TaskPhotoTable
import java.time.Instant
import java.time.ZoneId

fun ResultRow.hydrateTaskPhoto() = TaskPhotoEntity(
    id = TaskPhotoId(this[TaskPhotoTable.id]),
    taskId = TaskId(this[TaskPhotoTable.taskId]),
    photoUrl = this[TaskPhotoTable.photoUrl],
    uploadedBy = UserId(this[TaskPhotoTable.uploadedBy]),
    uploadedAt = Instant.ofEpochMilli(this[TaskPhotoTable.uploadedAt])
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
)
