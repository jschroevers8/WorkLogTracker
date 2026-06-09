package worklogtracker.application.usecases.task

import worklogtracker.application.dto.task.TaskResponse
import worklogtracker.application.exceptions.TaskAssignmentFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.exceptions.TaskNotFoundException
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId

class AssignTaskUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val userRepository: UserRepositoryInterface
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

            task.toResponse()
        } catch (e: Exception) {
            throw TaskAssignmentFailedException(e.message ?: "Unknown error")
        }
    }
}

