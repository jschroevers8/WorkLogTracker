package worklogtracker.backend.infrastructure.repositories

import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.hydrators.hydrateTask
import worklogtracker.backend.infrastructure.tables.TaskAssignmentTable
import worklogtracker.backend.infrastructure.tables.TaskTable
import java.time.LocalDateTime

class TaskRepository : TaskRepositoryInterface {
    override suspend fun findById(id: TaskId): TaskEntity? =
        transaction {
            TaskTable
                .selectAll()
                .where { TaskTable.id eq id.value }
                .map { it.hydrateTask() }
                .singleOrNull()
        }

    override suspend fun save(task: TaskEntity): TaskEntity =
        transaction {
            val now = LocalDateTime.now().toKotlinLocalDateTime()

            val id =
                TaskTable.insert {
                    it[projectId] = task.projectId.value
                    it[title] = task.title
                    it[description] = task.description
                    it[status] = task.status.name
                    it[createdBy] = task.createdBy.value
                    it[createdAt] = now
                    it[updatedAt] = now
                } get TaskTable.id

            task.copy(id = TaskId(id))
        }

    override suspend fun update(task: TaskEntity): Boolean =
        transaction {
            val now = LocalDateTime.now().toKotlinLocalDateTime()

            TaskTable.update({ TaskTable.id eq task.id!!.value }) {
                it[title] = task.title
                it[description] = task.description
                it[status] = task.status.name
                it[updatedAt] = now
            } > 0
        }

    override suspend fun delete(id: TaskId): Boolean =
        transaction {
            TaskTable.deleteWhere { TaskTable.id eq id.value } > 0
        }

    override suspend fun findByProject(projectId: ProjectId): List<TaskEntity> =
        transaction {
            TaskTable
                .selectAll()
                .where { TaskTable.projectId eq projectId.value }
                .map { it.hydrateTask() }
        }

    override suspend fun findByUser(userId: UserId): List<TaskEntity> =
        transaction {
            val taskIds =
                TaskAssignmentTable
                    .selectAll()
                    .where { TaskAssignmentTable.userId eq userId.value }
                    .map { it[TaskAssignmentTable.taskId] }
                    .distinct()

            if (taskIds.isEmpty()) return@transaction emptyList()

            TaskTable
                .selectAll()
                .where { TaskTable.id inList taskIds }
                .map { it.hydrateTask() }
        }

    override suspend fun findByStatus(status: TaskStatus): List<TaskEntity> =
        transaction {
            TaskTable
                .selectAll()
                .where { TaskTable.status eq status.name }
                .map { it.hydrateTask() }
        }
}
