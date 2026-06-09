package worklogtracker.application.usecases.worklog

import worklogtracker.application.dto.worklog.WorkLogResponse
import worklogtracker.application.exceptions.WorkLogCreationFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.factories.WorkLogFactory
import worklogtracker.domain.repositories.WorkLogRepositoryInterface
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class CreateManualWorkLogUseCase(
    private val workLogRepository: WorkLogRepositoryInterface,
    private val taskRepository: TaskRepositoryInterface,
    private val workLogFactory: WorkLogFactory
) {
    
    suspend operator fun invoke(
        userId: UserId,
        taskId: TaskId,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        notes: String? = null
    ): WorkLogResponse {
        return try {
            val task = taskRepository.findById(taskId)
                ?: throw Exception("Task not found")
            if (task.assignedUserId != userId) {
                throw Exception("Task not assigned to user")
            }
            
            val workLog = workLogFactory.create(
                taskId = taskId,
                userId = userId,
                startTime = startTime,
                endTime = endTime,
                notes = notes
            )
            
            workLogRepository.save(workLog).toResponse()
        } catch (e: Exception) {
            throw WorkLogCreationFailedException(e.message ?: "Unknown error")
        }
    }
}

