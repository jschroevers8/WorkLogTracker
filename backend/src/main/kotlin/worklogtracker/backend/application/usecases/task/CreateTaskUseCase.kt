package worklogtracker.backend.application.usecases.task

import worklogtracker.backend.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.backend.domain.entities.enums.NotificationType
import worklogtracker.backend.domain.entities.TaskAssignmentEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskAssignmentRepositoryInterface
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.backend.application.exceptions.TaskCreationFailedException
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.factories.TaskFactory
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class CreateTaskUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val taskAssignmentRepository: TaskAssignmentRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val taskFactory: TaskFactory,
    private val createNotificationUseCase: CreateNotificationUseCase
) {
    
    suspend operator fun invoke(
        userId: UserId,
        projectId: ProjectId,
        title: String,
        description: String?,
        assignedUserId: UserId
    ): TaskResponse {
        return try {
            val user = userRepository.findById(userId) ?: throw Exception("User not found")
            val assignedUser = userRepository.findById(assignedUserId) ?: throw Exception("Assigned user not found")
            
            val task = taskFactory.create(
                projectId = projectId,
                createdBy = userId,
                title = title,
                description = description
            )
            
            val savedTask = taskRepository.save(task)
            val taskId = savedTask.id ?: throw Exception("Failed to save task")

            // Create assignment
            val assignment = TaskAssignmentEntity(
                taskId = taskId,
                userId = assignedUserId,
                assignedAt = LocalDateTime.now(),
                status = TaskStatus.OPEN
            )
            taskAssignmentRepository.save(assignment)

            // Notify user
            createNotificationUseCase(
                userId = assignedUserId,
                title = "Nieuwe taak toegewezen",
                message = "Je bent toegewezen aan taak: ${savedTask.title}",
                type = NotificationType.TASK_ASSIGNED,
                taskId = taskId
            )
            
            TaskResponse(
                id = savedTask.id.value,
                projectId = savedTask.projectId.value,
                title = savedTask.title,
                description = savedTask.description,
                status = savedTask.status.name,
                createdBy = savedTask.createdBy.value,
                createdAt = savedTask.createdAt.toString(),
                updatedAt = savedTask.updatedAt.toString()
            )
        } catch (e: Exception) {
            throw TaskCreationFailedException(e.message ?: "Unknown error")
        }
    }
}
