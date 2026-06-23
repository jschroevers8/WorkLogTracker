package worklogtracker.backend.infrastructure.tables

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.datetime

object UserTable : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val firstName = varchar("first_name", 255)
    val lastName = varchar("last_name", 255)
    val role = varchar("role", 50)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    override val primaryKey = PrimaryKey(id)
}
