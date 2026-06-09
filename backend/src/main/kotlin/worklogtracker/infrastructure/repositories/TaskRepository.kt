package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.TaskEntity
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.hydrators.hydrateTask
import worklogtracker.infrastructure.tables.TaskTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

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

            TaskTable.insert {
                it[projectId] = task.projectId.value
                it[assignedUserId] = task.assignedUserId?.value
                it[title] = task.title
                it[description] = task.description
                it[estimatedHours] = task.estimatedHours
                it[actualHours] = task.actualHours
                it[deadline] = task.deadline?.let { date ->
                    Instant.from(
                        date.atZone(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[priority] = task.priority
                it[status] = task.status.name
                it[createdAt] = now
                it[updatedAt] = now
            }

            task
        }

    override suspend fun update(task: TaskEntity): Boolean =
        transaction {
            val now = Instant.now().toEpochMilli()

            TaskTable.update({ TaskTable.id eq task.id!!.value }) {
                it[assignedUserId] = task.assignedUserId?.value
                it[title] = task.title
                it[description] = task.description
                it[estimatedHours] = task.estimatedHours
                it[actualHours] = task.actualHours
                it[deadline] = task.deadline?.let { date ->
                    Instant.from(
                        date.atZone(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[priority] = task.priority
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
            TaskTable
                .selectAll()
                .where { TaskTable.assignedUserId eq userId.value }
                .map { it.hydrateTask() }
        }

    override suspend fun findByStatus(status: TaskStatus): List<TaskEntity> =
        transaction {
            TaskTable
                .selectAll()
                .where { TaskTable.status eq status.name }
                .map { it.hydrateTask() }
        }

    override suspend fun findUpcomingDeadlines(daysAhead: Int): List<TaskEntity> =
        transaction {
            val now = Instant.now().toEpochMilli()
            val future = now + (daysAhead * 24L * 60L * 60L * 1000L)

            TaskTable
                .selectAll()
                .where {
                    (TaskTable.deadline greaterEq now) and
                            (TaskTable.deadline lessEq future) and
                            (TaskTable.status neq TaskStatus.COMPLETED.name) and
                            (TaskTable.status neq TaskStatus.CANCELLED.name)
                }
                .map { it.hydrateTask() }
        }
}