package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.AttachmentEntity
import worklogtracker.domain.repositories.AttachmentRepositoryInterface
import worklogtracker.domain.valueobjects.attachment.AttachmentId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.infrastructure.hydrators.hydrateAttachment
import worklogtracker.infrastructure.tables.AttachmentTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.ZoneId

class AttachmentRepository : AttachmentRepositoryInterface {
    
    override suspend fun findById(id: AttachmentId): AttachmentEntity? =
        transaction {
            AttachmentTable
                .selectAll()
                .where { AttachmentTable.id eq id.value }
                .map { it.hydrateAttachment() }
                .singleOrNull()
        }
    
    override suspend fun save(attachment: AttachmentEntity): AttachmentEntity {
        transaction {
            AttachmentTable.insert {
                it[taskId] = attachment.taskId.value
                it[fileName] = attachment.fileName
                it[filePath] = attachment.filePath
                it[fileSize] = attachment.fileSize
                it[uploadedByUserId] = attachment.uploadedByUserId.value
                it[uploadedAt] = Instant.from(attachment.uploadedAt.atZone(ZoneId.systemDefault())).toEpochMilli()
                it[createdAt] = Instant.from(attachment.createdAt.atZone(ZoneId.systemDefault())).toEpochMilli()
            }
        }

        return attachment
    }
    
    override suspend fun delete(id: AttachmentId): Boolean =
        transaction {
            AttachmentTable.deleteWhere { AttachmentTable.id eq id.value } > 0
        }
    
    override suspend fun findByTask(taskId: TaskId): List<AttachmentEntity> =
        transaction {
            AttachmentTable
                .selectAll()
                .where { AttachmentTable.taskId eq taskId.value }
                .map { it.hydrateAttachment() }
    }
}

