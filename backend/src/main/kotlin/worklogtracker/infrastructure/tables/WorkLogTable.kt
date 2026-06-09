package worklogtracker.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object WorkLogTable : Table("work_logs") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val userId = integer("user_id").references(UserTable.id)
    val startTime = long("start_time")
    val endTime = long("end_time").nullable()
    val durationMinutes = integer("duration_minutes").nullable()
    val notes = text("notes").nullable()
    val isSynced = bool("is_synced").default(false)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    override val primaryKey = PrimaryKey(id)
}
