package worklogtracker.application.usecases.worklog

import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.application.exceptions.WorkLogCreationFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.factories.WorkLogFactory
import worklogtracker.domain.repositories.TimerSessionRepositoryInterface
import worklogtracker.domain.repositories.WorkLogRepositoryInterface
import worklogtracker.domain.valueobjects.timer.TimerSessionId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class StopTimerUseCase(
    private val timerRepository: TimerSessionRepositoryInterface,
    private val workLogRepository: WorkLogRepositoryInterface,
    private val workLogFactory: WorkLogFactory
) {
    
    suspend operator fun invoke(
        userId: UserId,
        sessionId: TimerSessionId,
        notes: String? = null
    ): WorkLogResponse {
        return try {
            var session = timerRepository.findById(sessionId) 
                ?: throw Exception("Timer session not found")
            
            if (session.userId != userId) {
                throw Exception("User doesn't own this timer")
            }
            
            if (!session.isRunning) {
                throw Exception("Timer is already stopped")
            }
            
            val stoppedAt = LocalDateTime.now()
            session = session.stop(stoppedAt)
            timerRepository.update(session)
            
            val workLog = workLogFactory.create(
                taskId = session.taskId,
                userId = userId,
                startTime = session.startedAt,
                endTime = stoppedAt,
                notes = notes
            )
            
            workLogRepository.save(workLog).toResponse()
        } catch (e: Exception) {
            throw WorkLogCreationFailedException(e.message ?: "Unknown error")
        }
    }
}
