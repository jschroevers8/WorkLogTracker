package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object ProjectTable : Table("projects") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val status = varchar("status", 50)
    val startDate = long("start_date").nullable()
    val endDate = long("end_date").nullable()
    val createdById = integer("created_by_id").references(UserTable.id)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    override val primaryKey = PrimaryKey(id)
}
