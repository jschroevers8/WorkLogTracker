package worklogtracker.backend.application.usecases.task

import worklogtracker.backend.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.backend.domain.entities.enums.NotificationType
import worklogtracker.backend.domain.entities.TaskAssignmentEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.application.exceptions.TaskAssignmentFailedException
import worklogtracker.backend.domain.exceptions.TaskNotFoundException
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskAssignmentRepositoryInterface
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class AssignTaskUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val taskAssignmentRepository: TaskAssignmentRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val createNotificationUseCase: CreateNotificationUseCase
) {
    
    suspend operator fun invoke(
        userId: UserId,
        taskId: TaskId,
        assignedUserId: UserId
    ): Boolean {
        return try {
            val user = userRepository.findById(userId) ?: throw Exception("User not found")
            // user.ensureIsAdmin() 
            
            val assignedUser = userRepository.findById(assignedUserId) 
                ?: throw Exception("Assigned user not found")
            
            val task = taskRepository.findById(taskId) 
                ?: throw TaskNotFoundException(taskId.value.toString())
            
            val assignment = TaskAssignmentEntity(
                taskId = taskId,
                userId = assignedUserId,
                assignedAt = LocalDateTime.now(),
                status = TaskStatus.OPEN
            )
            
            taskAssignmentRepository.save(assignment)

            createNotificationUseCase(
                userId = assignedUserId,
                title = "Nieuwe taak toegewezen",
                message = "Je bent toegewezen aan taak: ${task.title}",
                type = NotificationType.TASK_ASSIGNED,
                taskId = taskId
            )

            true
        } catch (e: Exception) {
            throw TaskAssignmentFailedException(e.message ?: "Unknown error")
        }
    }
}

