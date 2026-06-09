package worklogtracker.application.usecases.task

import worklogtracker.application.dto.task.TaskResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.exceptions.TaskNotFoundException
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId

class UpdateTaskStatusUseCase(
    private val taskRepository: TaskRepositoryInterface
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

        return task.toResponse()
    }
}

