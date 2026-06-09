package worklogtracker.infrastructure.repositories

import worklogtracker.domain.entities.TimerSessionEntity
import worklogtracker.domain.repositories.TimerSessionRepositoryInterface
import worklogtracker.domain.valueobjects.timer.TimerSessionId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.infrastructure.hydrators.hydrateTimerSession
import worklogtracker.infrastructure.tables.TimerSessionTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.ZoneId

class TimerSessionRepository : TimerSessionRepositoryInterface {

    override suspend fun findById(id: TimerSessionId): TimerSessionEntity? =
        transaction {
            TimerSessionTable
                .selectAll()
                .where { TimerSessionTable.id eq id.value }
                .map { it.hydrateTimerSession() }
                .singleOrNull()
        }

    override suspend fun save(session: TimerSessionEntity): TimerSessionEntity =
        transaction {
            val now = Instant.now().toEpochMilli()

            TimerSessionTable.insert {
                it[taskId] = session.taskId.value
                it[userId] = session.userId.value
                it[startedAt] = Instant.from(
                    session.startedAt.atZone(ZoneId.systemDefault())
                ).toEpochMilli()
                it[endedAt] = session.endedAt?.let { date ->
                    Instant.from(
                        date.atZone(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[isRunning] = session.isRunning
                it[createdAt] = now
                it[updatedAt] = now
            }

            session
        }

    override suspend fun update(session: TimerSessionEntity): Boolean =
        transaction {
            val now = Instant.now().toEpochMilli()

            TimerSessionTable.update({ TimerSessionTable.id eq session.id!!.value }) {
                it[endedAt] = session.endedAt?.let { date ->
                    Instant.from(
                        date.atZone(ZoneId.systemDefault())
                    ).toEpochMilli()
                }
                it[isRunning] = session.isRunning
                it[updatedAt] = now
            } > 0
        }

    override suspend fun findActiveByUser(userId: UserId): TimerSessionEntity? =
        transaction {
            TimerSessionTable
                .selectAll()
                .where {
                    (TimerSessionTable.userId eq userId.value) and
                            (TimerSessionTable.isRunning eq true)
                }
                .map { it.hydrateTimerSession() }
                .singleOrNull()
        }

    override suspend fun findAll(userId: UserId): List<TimerSessionEntity> =
        transaction {
            TimerSessionTable
                .selectAll()
                .where { TimerSessionTable.userId eq userId.value }
                .map { it.hydrateTimerSession() }
        }
}