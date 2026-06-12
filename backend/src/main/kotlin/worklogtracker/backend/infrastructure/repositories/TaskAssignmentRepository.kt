package worklogtracker.backend.infrastructure.repositories

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import worklogtracker.backend.domain.entities.TaskAssignmentEntity
import worklogtracker.backend.domain.repositories.TaskAssignmentRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.infrastructure.hydrators.hydrateTaskAssignment
import worklogtracker.backend.infrastructure.tables.TaskAssignmentTable
import java.time.Instant

class TaskAssignmentRepository : TaskAssignmentRepositoryInterface {
    override suspend fun findById(id: TaskAssignmentId): TaskAssignmentEntity? = transaction {
        TaskAssignmentTable
            .selectAll()
            .where { TaskAssignmentTable.id eq id.value }
            .map { it.hydrateTaskAssignment() }
            .singleOrNull()
    }

    override suspend fun save(assignment: TaskAssignmentEntity): TaskAssignmentEntity = transaction {
        val now = Instant.now().toEpochMilli()
        val id = TaskAssignmentTable.insert {
            it[taskId] = assignment.taskId.value
            it[userId] = assignment.userId.value
            it[assignedAt] = now
            it[status] = assignment.status.name
        } get TaskAssignmentTable.id
        assignment.copy(id = TaskAssignmentId(id))
    }

    override suspend fun update(assignment: TaskAssignmentEntity): Boolean = transaction {
        TaskAssignmentTable.update({ TaskAssignmentTable.id eq assignment.id!!.value }) {
            it[status] = assignment.status.name
        } > 0
    }

    override suspend fun findByTask(taskId: TaskId): List<TaskAssignmentEntity> = transaction {
        TaskAssignmentTable
            .selectAll()
            .where { TaskAssignmentTable.taskId eq taskId.value }
            .map { it.hydrateTaskAssignment() }
    }

    override suspend fun findByUser(userId: UserId): List<TaskAssignmentEntity> = transaction {
        TaskAssignmentTable
            .selectAll()
            .where { TaskAssignmentTable.userId eq userId.value }
            .map { it.hydrateTaskAssignment() }
    }
}
