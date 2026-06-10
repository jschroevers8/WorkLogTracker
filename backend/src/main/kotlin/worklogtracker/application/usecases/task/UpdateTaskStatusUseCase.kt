package worklogtracker.application.usecases.task

import worklogtracker.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.domain.entities.enums.NotificationType
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.exceptions.TaskNotFoundException
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId

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
        
        if (task.assignedUserId != null && task.assignedUserId != userId) {
            throw Exception("You don't have permission to update this task")
        }
        
        task = task.updateStatus(newStatus)
        taskRepository.update(task)

        if (newStatus == TaskStatus.COMPLETED) {
            createNotificationUseCase(
                userId = task.createdBy,
                title = "Taak voltooid",
                message = "De taak '${task.title}' is voltooid door ${task.assignedUserId}.",
                type = NotificationType.TASK_COMPLETED,
                taskId = taskId
            )
        }

        return task.toResponse()
    }
}

