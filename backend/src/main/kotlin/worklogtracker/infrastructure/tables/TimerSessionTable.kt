package worklogtracker.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object TimerSessionTable : Table("timer_sessions") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val userId = integer("user_id").references(UserTable.id)
    val startedAt = long("started_at")
    val endedAt = long("ended_at").nullable()
    val isRunning = bool("is_running").default(true)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    override val primaryKey = PrimaryKey(id)
}
