package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.datetime

object TaskPhotoTable : Table("task_photos") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val photoUrl = text("photo_url")
    val uploadedBy = integer("uploaded_by").references(UserTable.id)
    val uploadedAt = datetime("uploaded_at")
    override val primaryKey = PrimaryKey(id)
}
