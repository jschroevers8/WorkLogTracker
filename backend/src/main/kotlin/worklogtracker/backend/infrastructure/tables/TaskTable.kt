package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object TaskTable : Table("tasks") {
    val id = integer("id").autoIncrement()
    val projectId = integer("project_id").references(ProjectTable.id)
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val status = varchar("status", 50)
    val createdBy = integer("created_by").references(UserTable.id)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    override val primaryKey = PrimaryKey(id)
}
