package worklogtracker.domain.repositories

import worklogtracker.domain.entities.TimerSessionEntity
import worklogtracker.domain.valueobjects.timer.TimerSessionId
import worklogtracker.domain.valueobjects.user.UserId

interface TimerSessionRepositoryInterface {
    suspend fun findById(id: TimerSessionId): TimerSessionEntity?
    suspend fun save(session: TimerSessionEntity): TimerSessionEntity
    suspend fun update(session: TimerSessionEntity): Boolean
    suspend fun findActiveByUser(userId: UserId): TimerSessionEntity?
    suspend fun findAll(userId: UserId): List<TimerSessionEntity>
}

