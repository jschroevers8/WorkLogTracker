package worklogtracker.application.usecases.worklog

import worklogtracker.application.dto.worklog.TimerSessionResponse
import worklogtracker.application.exceptions.TimerSessionFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.factories.TimerSessionFactory
import worklogtracker.domain.repositories.TimerSessionRepositoryInterface
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.validations.timer.ActiveTimerValidator
import java.time.LocalDateTime

class StartTimerUseCase(
    private val timerRepository: TimerSessionRepositoryInterface,
    private val taskRepository: TaskRepositoryInterface,
    private val activeTimerValidator: ActiveTimerValidator,
    private val timerFactory: TimerSessionFactory
) {
    
    suspend operator fun invoke(
        userId: UserId,
        taskId: TaskId
    ): TimerSessionResponse {
        return try {
            activeTimerValidator.validate(userId)
            
            val task = taskRepository.findById(taskId)
                ?: throw Exception("Task not found")
            if (task.assignedUserId != userId) {
                throw Exception("Task not assigned to user")
            }
            
            val session = timerFactory.create(
                taskId = taskId,
                userId = userId,
                startedAt = LocalDateTime.now()
            )
            
            timerRepository.save(session).toResponse()
        } catch (e: Exception) {
            throw TimerSessionFailedException(e.message ?: "Unknown error")
        }
    }
}
