package worklogtracker.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object PendingSyncTable : Table("pending_syncs") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val entityType = varchar("entity_type", 50)
    val entityId = varchar("entity_id", 36)
    val operation = varchar("operation", 50)
    val payload = text("payload")
    val syncStatus = varchar("sync_status", 50)
    val syncError = text("sync_error").nullable()
    val createdAt = long("created_at")
    val syncedAt = long("synced_at").nullable()
    val lastModified = long("last_modified").nullable()
    override val primaryKey = PrimaryKey(id)
}
