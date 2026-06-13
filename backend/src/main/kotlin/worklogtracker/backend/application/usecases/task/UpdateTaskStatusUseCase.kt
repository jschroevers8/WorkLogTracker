package worklogtracker.backend.application.usecases.task

import worklogtracker.backend.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.backend.domain.entities.enums.NotificationType
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.exceptions.TaskNotFoundException
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId

class UpdateTaskStatusUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val createNotificationUseCase: CreateNotificationUseCase
) {
    
    suspend operator fun invoke(
        userId: UserId,
        taskId: TaskId,
        newStatus: TaskStatus
    ): TaskResponse {
        var task = taskRepository.findById(taskId) 
            ?: throw TaskNotFoundException(taskId.value.toString())
        
        task = task.updateStatus(newStatus)
        taskRepository.update(task)

        if (newStatus == TaskStatus.COMPLETED) {
            createNotificationUseCase(
                userId = task.createdBy,
                title = "Taak voltooid",
                message = "De taak '${task.title}' is voltooid",
                type = NotificationType.TASK_COMPLETED,
                taskId = taskId
            )
        }

        return task.toResponse()
    }
}

