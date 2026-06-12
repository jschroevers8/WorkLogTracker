package worklogtracker.backend.application.usecases.worklog

import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.backend.application.exceptions.WorkLogCreationFailedException
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.factories.WorkLogFactory
import worklogtracker.backend.domain.repositories.WorkLogRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
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
        notes: String? = null,
        photoUrl: String? = null,
        latitude: Double? = null,
        longitude: Double? = null
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
                notes = notes,
                photoUrl = photoUrl,
                latitude = latitude,
                longitude = longitude
            )
            
            workLogRepository.save(workLog).toResponse()
        } catch (e: Exception) {
            throw WorkLogCreationFailedException(e.message ?: "Unknown error")
        }
    }
}

