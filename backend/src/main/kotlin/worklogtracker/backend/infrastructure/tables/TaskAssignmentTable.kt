package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object TaskAssignmentTable : Table("task_assignments") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val userId = integer("user_id").references(UserTable.id)
    val assignedAt = long("assigned_at")
    val status = varchar("status", 50)
    override val primaryKey = PrimaryKey(id)
}
