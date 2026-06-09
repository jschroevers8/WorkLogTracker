package worklogtracker.application.usecases.task

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.entities.enums.TaskStatus
import worklogtracker.domain.repositories.TaskRepositoryInterface
import worklogtracker.domain.valueobjects.project.ProjectId
import worklogtracker.domain.valueobjects.user.UserId

class ListTasksUseCase(
    private val taskRepository: TaskRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId,
        projectId: ProjectId? = null,
        status: TaskStatus? = null
    ): List<TaskResponse> {
        val tasks = if (projectId != null) {
            taskRepository.findByProject(projectId)
        } else {
            taskRepository.findByUser(userId)
        }

        return tasks.map { it.toResponse() }
    }
}

