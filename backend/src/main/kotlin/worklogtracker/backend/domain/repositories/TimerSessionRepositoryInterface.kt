package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.TimerSessionEntity
import worklogtracker.backend.domain.valueobjects.timer.TimerSessionId
import worklogtracker.backend.domain.valueobjects.user.UserId

interface TimerSessionRepositoryInterface {
    suspend fun findById(id: TimerSessionId): TimerSessionEntity?
    suspend fun save(session: TimerSessionEntity): TimerSessionEntity
    suspend fun update(session: TimerSessionEntity): Boolean
    suspend fun findActiveByUser(userId: UserId): TimerSessionEntity?
    suspend fun findAll(userId: UserId): List<TimerSessionEntity>
}

