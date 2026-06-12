package worklogtracker.backend.infrastructure.repositories

import worklogtracker.backend.domain.entities.TaskEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.hydrators.hydrateTask
import worklogtracker.backend.infrastructure.tables.TaskTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

import worklogtracker.backend.infrastructure.tables.TaskAssignmentTable
import org.jetbrains.exposed.sql.JoinType

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
            val now = Instant.now().toEpochMilli()

            val id = TaskTable.insert {
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
            val now = Instant.now().toEpochMilli()

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
            // Find tasks where the user is assigned
            (TaskTable innerJoin TaskAssignmentTable)
                .selectAll()
                .where { TaskAssignmentTable.userId eq userId.value }
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