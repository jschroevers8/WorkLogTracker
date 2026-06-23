package worklogtracker.backend.infrastructure.repositories

import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import worklogtracker.backend.domain.entities.TaskLocationEntity
import worklogtracker.backend.domain.repositories.TaskLocationRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskLocationId
import worklogtracker.backend.infrastructure.hydrators.hydrateTaskLocation
import worklogtracker.backend.infrastructure.tables.TaskLocationTable
import java.time.LocalDateTime

class TaskLocationRepository : TaskLocationRepositoryInterface {
    override suspend fun findById(id: TaskLocationId): TaskLocationEntity? =
        transaction {
            TaskLocationTable
                .selectAll()
                .where { TaskLocationTable.id eq id.value }
                .map { it.hydrateTaskLocation() }
                .singleOrNull()
        }

    override suspend fun save(location: TaskLocationEntity): TaskLocationEntity =
        transaction {
            val now = LocalDateTime.now().toKotlinLocalDateTime()
            val id =
                TaskLocationTable.insert {
                    it[taskId] = location.taskId.value
                    it[latitude] = location.latitude
                    it[longitude] = location.longitude
                    it[recordedAt] = now
                } get TaskLocationTable.id
            location.copy(id = TaskLocationId(id))
        }

    override suspend fun findByTask(taskId: TaskId): List<TaskLocationEntity> =
        transaction {
            TaskLocationTable
                .selectAll()
                .where { TaskLocationTable.taskId eq taskId.value }
                .map { it.hydrateTaskLocation() }
        }
}
