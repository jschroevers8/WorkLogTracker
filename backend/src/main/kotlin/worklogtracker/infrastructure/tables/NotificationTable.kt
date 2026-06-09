package worklogtracker.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object NotificationTable : Table("notifications") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val taskId = integer("task_id").references(TaskTable.id).nullable()
    val title = varchar("title", 255)
    val message = text("message")
    val type = varchar("type", 50)
    val sentAt = long("sent_at")
    val isRead = bool("is_read").default(false)
    val createdAt = long("created_at")
    override val primaryKey = PrimaryKey(id)
}
