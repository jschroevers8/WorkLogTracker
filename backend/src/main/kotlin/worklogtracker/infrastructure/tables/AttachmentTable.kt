package worklogtracker.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object AttachmentTable : Table("attachments") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val fileName = varchar("file_name", 255)
    val filePath = varchar("file_path", 1024)
    val fileSize = integer("file_size")
    val uploadedByUserId = integer("uploaded_by_id").references(UserTable.id)
    val uploadedAt = long("uploaded_at")
    val createdAt = long("created_at")
    override val primaryKey = PrimaryKey(id)
}
