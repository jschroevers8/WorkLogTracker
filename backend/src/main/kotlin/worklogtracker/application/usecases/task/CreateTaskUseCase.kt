package worklogtracker.application.usecases.task

import worklogtracker.application.dto.task.TaskResponse
import worklogtracker.application.exceptions.TaskCreationFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.entities.enums.Priority
import worklogtracker.domain.factories.TaskFactory
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId
import java.math.BigDecimal
import java.time.LocalDateTime

class CreateTaskUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val taskFactory: TaskFactory
) {
    
    suspend operator fun invoke(
        userId: UserId,
        projectId: ProjectId,
        title: String,
        description: String?,
        estimatedHours: BigDecimal,
        deadline: LocalDateTime?,
        priority: Priority
    ): TaskResponse {
        return try {
            val user = userRepository.findById(userId) ?: throw Exception("User not found")
            user.ensureIsAdmin()
            
            val task = taskFactory.create(
                projectId = projectId,
                title = title,
                description = description,
                estimatedHours = estimatedHours,
                deadline = deadline,
                priority = priority
            )
            
            taskRepository.save(task).toResponse()
        } catch (e: Exception) {
            throw TaskCreationFailedException(e.message ?: "Unknown error")
        }
    }
}
