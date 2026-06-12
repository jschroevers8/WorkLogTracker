package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object TaskLocationTable : Table("task_locations") {
    val id = integer("id").autoIncrement()
    val taskId = integer("task_id").references(TaskTable.id)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val recordedAt = long("recorded_at")
    override val primaryKey = PrimaryKey(id)
}
