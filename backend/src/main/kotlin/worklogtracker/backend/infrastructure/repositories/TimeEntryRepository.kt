package worklogtracker.backend.infrastructure.repositories

import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import worklogtracker.backend.domain.entities.TimeEntryEntity
import worklogtracker.backend.domain.repositories.TimeEntryRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.domain.valueobjects.worklog.TimeEntryId
import worklogtracker.backend.infrastructure.hydrators.hydrateTimeEntry
import worklogtracker.backend.infrastructure.tables.TimeEntryTable

class TimeEntryRepository : TimeEntryRepositoryInterface {
    override suspend fun findById(id: TimeEntryId): TimeEntryEntity? =
        transaction {
            TimeEntryTable
                .selectAll()
                .where { TimeEntryTable.id eq id.value }
                .map { it.hydrateTimeEntry() }
                .singleOrNull()
        }

    override suspend fun save(timeEntry: TimeEntryEntity): TimeEntryEntity =
        transaction {
            val id =
                TimeEntryTable.insert {
                    it[taskAssignmentId] = timeEntry.taskAssignmentId.value
                    it[userId] = timeEntry.userId.value
                    it[hours] = timeEntry.hours
                    it[description] = timeEntry.description
                    it[aiDescription] = timeEntry.aiDescription
                    it[createdAt] = timeEntry.createdAt.toKotlinLocalDateTime()
                } get TimeEntryTable.id
            timeEntry.copy(id = TimeEntryId(id))
        }

    override suspend fun findByAssignment(taskAssignmentId: TaskAssignmentId): List<TimeEntryEntity> =
        transaction {
            TimeEntryTable
                .selectAll()
                .where { TimeEntryTable.taskAssignmentId eq taskAssignmentId.value }
                .map { it.hydrateTimeEntry() }
        }

    override suspend fun findByUser(userId: UserId): List<TimeEntryEntity> =
        transaction {
            TimeEntryTable
                .selectAll()
                .where { TimeEntryTable.userId eq userId.value }
                .map { it.hydrateTimeEntry() }
        }
}
