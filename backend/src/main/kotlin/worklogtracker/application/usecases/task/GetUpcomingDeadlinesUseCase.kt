package worklogtracker.application.usecases.task

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.repositories.TaskRepositoryInterface

class GetUpcomingDeadlinesUseCase(
    private val taskRepository: TaskRepositoryInterface
) {
    
    suspend operator fun invoke(daysAhead: Int = 7): List<TaskResponse> {
        return taskRepository.findUpcomingDeadlines(daysAhead).map { it.toResponse() }
    }
}

