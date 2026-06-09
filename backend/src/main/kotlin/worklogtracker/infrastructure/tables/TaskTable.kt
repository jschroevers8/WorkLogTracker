package worklogtracker.infrastructure.tables

import worklogtracker.domain.entities.enums.Priority
import org.jetbrains.exposed.sql.Table

object TaskTable : Table("tasks") {
    val id = integer("id").autoIncrement()
    val projectId = integer("project_id").references(ProjectTable.id)
    val assignedUserId = integer("assigned_user_id").references(UserTable.id).nullable()
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val estimatedHours = decimal("estimated_hours", 8, 2)
    val actualHours = decimal("actual_hours", 8, 2)
    val deadline = long("deadline").nullable()
    val priority = enumerationByName("priority", 20, Priority::class)
    val status = varchar("status", 50)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    override val primaryKey = PrimaryKey(id)
}
