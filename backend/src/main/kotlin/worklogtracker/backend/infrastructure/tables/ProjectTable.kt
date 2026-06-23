package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.datetime.datetime

object ProjectTable : Table("projects") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val status = varchar("status", 50)
    val startDate = date("start_date").nullable()
    val endDate = date("end_date").nullable()
    val createdById = integer("created_by_id").references(UserTable.id)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    override val primaryKey = PrimaryKey(id)
}
