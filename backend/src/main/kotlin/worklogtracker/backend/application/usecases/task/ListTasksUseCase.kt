package worklogtracker.backend.application.usecases.task

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId

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

