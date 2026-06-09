package worklogtracker.infrastructure.hydrators

import worklogtracker.domain.entities.AttachmentEntity
import worklogtracker.domain.valueobjects.attachment.AttachmentId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.tables.AttachmentTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun ResultRow.hydrateAttachment() =
    AttachmentEntity(
        id = AttachmentId(this[AttachmentTable.id]),
        taskId = TaskId(this[AttachmentTable.taskId]),
        fileName = this[AttachmentTable.fileName],
        filePath = this[AttachmentTable.filePath],
        fileSize = this[AttachmentTable.fileSize],
        uploadedByUserId = UserId(this[AttachmentTable.uploadedByUserId]),
        uploadedAt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this[AttachmentTable.uploadedAt]),
            ZoneId.systemDefault()
        ),
        createdAt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this[AttachmentTable.createdAt]),
            ZoneId.systemDefault()
        )
    )
