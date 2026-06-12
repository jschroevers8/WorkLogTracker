package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object TaskPhotoTable : Table("task_photos") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val photoUrl = varchar("photo_url", 512)
    val uploadedBy = integer("uploaded_by").references(UserTable.id)
    val uploadedAt = long("uploaded_at")
    override val primaryKey = PrimaryKey(id)
}
