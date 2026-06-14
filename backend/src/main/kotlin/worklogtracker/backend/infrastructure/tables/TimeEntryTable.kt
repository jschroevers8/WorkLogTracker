package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TimeEntryTable : Table("time_entries") {
    val id = integer("id").autoIncrement()
    val taskAssignmentId = integer("task_assignment_id").references(TaskAssignmentTable.id)
    val userId = integer("user_id").references(UserTable.id)
    val hours = decimal("hours", 8, 2)
    val description = text("description").nullable()
    val aiDescription = text("ai_description").nullable()
    val createdAt = datetime("created_at")
    override val primaryKey = PrimaryKey(id)
}
