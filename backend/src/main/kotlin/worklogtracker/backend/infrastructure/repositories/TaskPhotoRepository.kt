package worklogtracker.backend.infrastructure.repositories

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import worklogtracker.backend.domain.entities.TaskPhotoEntity
import worklogtracker.backend.domain.repositories.TaskPhotoRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskPhotoId
import worklogtracker.backend.infrastructure.hydrators.hydrateTaskPhoto
import worklogtracker.backend.infrastructure.tables.TaskPhotoTable
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

class TaskPhotoRepository : TaskPhotoRepositoryInterface {
    override suspend fun findById(id: TaskPhotoId): TaskPhotoEntity? = transaction {
        TaskPhotoTable
            .selectAll()
            .where { TaskPhotoTable.id eq id.value }
            .map { it.hydrateTaskPhoto() }
            .singleOrNull()
    }

    override suspend fun save(photo: TaskPhotoEntity): TaskPhotoEntity = transaction {
        val now = LocalDateTime.now().toKotlinLocalDateTime()
        val id = TaskPhotoTable.insert {
            it[taskId] = photo.taskId.value
            it[photoUrl] = photo.photoUrl
            it[uploadedBy] = photo.uploadedBy.value
            it[uploadedAt] = now
        } get TaskPhotoTable.id
        photo.copy(id = TaskPhotoId(id))
    }

    override suspend fun findByTask(taskId: TaskId): List<TaskPhotoEntity> = transaction {
        TaskPhotoTable
            .selectAll()
            .where { TaskPhotoTable.taskId eq taskId.value }
            .map { it.hydrateTaskPhoto() }
    }
}
