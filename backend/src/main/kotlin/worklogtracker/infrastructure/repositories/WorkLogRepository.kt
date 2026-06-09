package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.WorkLogEntity
import worklogtracker.domain.repositories.WorkLogRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.valueobjects.worklog.WorkLogId
import worklogtracker.infrastructure.hydrators.hydrateWorkLog
import worklogtracker.infrastructure.tables.WorkLogTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class WorkLogRepository : WorkLogRepositoryInterface {

    override suspend fun findById(id: WorkLogId): WorkLogEntity? =
        transaction {
            WorkLogTable
                .selectAll()
                .where { WorkLogTable.id eq id.value }
                .map { it.hydrateWorkLog() }
                .singleOrNull()
        }

    override suspend fun save(workLog: WorkLogEntity): WorkLogEntity =
        transaction {
            val now = Instant.now().toEpochMilli()

            WorkLogTable.insert {
                it[taskId] = workLog.taskId.value
                it[userId] = workLog.userId.value
                it[startTime] = Instant.from(
                    workLog.startTime.atZone(ZoneId.systemDefault())
                ).toEpochMilli()
                it[endTime] = workLog.endTime?.let { date ->
                    Instant.from(
                        date.atZone(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[durationMinutes] = workLog.durationMinutes
                it[notes] = workLog.notes
                it[isSynced] = workLog.isSynced
                it[createdAt] = now
                it[updatedAt] = now
            }

            workLog
        }

    override suspend fun update(workLog: WorkLogEntity): Boolean =
        transaction {
            val now = Instant.now().toEpochMilli()

            WorkLogTable.update({ WorkLogTable.id eq workLog.id!!.value }) {
                it[endTime] = workLog.endTime?.let { date ->
                    Instant.from(date.atZone(ZoneId.systemDefault()))
                        .toEpochMilli()
                }
                it[durationMinutes] = workLog.durationMinutes
                it[isSynced] = workLog.isSynced
                it[updatedAt] = now
            } > 0
        }

    override suspend fun delete(id: WorkLogId): Boolean =
        transaction {
            WorkLogTable.deleteWhere { WorkLogTable.id eq id.value } > 0
        }

    override suspend fun findByTask(taskId: TaskId): List<WorkLogEntity> =
        transaction {
            WorkLogTable
                .selectAll()
                .where { WorkLogTable.taskId eq taskId.value }
                .map { it.hydrateWorkLog() }
        }

    override suspend fun findByUser(
        userId: UserId,
        from: LocalDateTime?,
        to: LocalDateTime?
    ): List<WorkLogEntity> =
        transaction {
            WorkLogTable
                .selectAll()
                .where { WorkLogTable.userId eq userId.value }
                .let { baseQuery ->
                    var query = baseQuery

                    from?.let {
                        val fromMillis = Instant.from(
                            it.atZone(ZoneId.systemDefault())
                        ).toEpochMilli()

                        query = query.andWhere {
                            WorkLogTable.createdAt greaterEq fromMillis
                        }
                    }

                    to?.let {
                        val toMillis = Instant.from(
                            it.atZone(ZoneId.systemDefault())
                        ).toEpochMilli()

                        query = query.andWhere {
                            WorkLogTable.createdAt lessEq toMillis
                        }
                    }

                    query
                }
                .map { it.hydrateWorkLog() }
        }

    override suspend fun findUnsyncedByUser(userId: UserId): List<WorkLogEntity> =
        transaction {
            WorkLogTable
                .selectAll()
                .where {
                    (WorkLogTable.userId eq userId.value) and
                            (WorkLogTable.isSynced eq false)
                }
                .map { it.hydrateWorkLog() }
        }
}