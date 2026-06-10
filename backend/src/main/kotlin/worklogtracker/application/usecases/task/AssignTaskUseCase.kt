package worklogtracker.application.usecases.task

import worklogtracker.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.domain.entities.enums.NotificationType
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.application.exceptions.TaskAssignmentFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.exceptions.TaskNotFoundException
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId

class AssignTaskUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val createNotificationUseCase: CreateNotificationUseCase
) {
    
    suspend operator fun invoke(
        userId: UserId,
        taskId: TaskId,
        assignedUserId: UserId
    ): TaskResponse {
        return try {
            val user = userRepository.findById(userId) ?: throw Exception("User not found")
            user.ensureIsAdmin()
            
            val assignedUser = userRepository.findById(assignedUserId) 
                ?: throw Exception("Assigned user not found")
            assignedUser.ensureIsTeamMember()
            
            var task = taskRepository.findById(taskId) 
                ?: throw TaskNotFoundException(taskId.value.toString())
            
            task = task.assignTo(assignedUserId)
            taskRepository.update(task)

            createNotificationUseCase(
                userId = assignedUserId,
                title = "Nieuwe taak toegewezen",
                message = "Je bent toegewezen aan taak: ${task.title}",
                type = NotificationType.TASK_ASSIGNED,
                taskId = taskId
            )

            task.toResponse()
        } catch (e: Exception) {
            throw TaskAssignmentFailedException(e.message ?: "Unknown error")
        }
    }
}

