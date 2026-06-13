package worklogtracker.backend.application.usecases.task

import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskLocationRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskPhotoRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.valueobjects.user.UserId

class ListTasksUseCase(
    private val taskRepository: TaskRepositoryInterface,
    private val taskPhotoRepository: TaskPhotoRepositoryInterface,
    private val taskLocationRepository: TaskLocationRepositoryInterface,
    private val taskAssignmentRepository: worklogtracker.backend.domain.repositories.TaskAssignmentRepositoryInterface,
    private val timeEntryRepository: worklogtracker.backend.domain.repositories.TimeEntryRepositoryInterface
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

        val assignments = taskAssignmentRepository.findByUser(userId)

        return tasks.map { task ->
            val taskId = task.id!!
            val photos = taskPhotoRepository.findByTask(taskId)
            val locations = taskLocationRepository.findByTask(taskId)
            val assignment = assignments.find { it.taskId == taskId }
            val timeEntries = assignment?.let { timeEntryRepository.findByAssignment(it.id!!) } ?: emptyList()
            val totalHours = timeEntries.sumOf { it.hours.toDouble() }
            task.toResponse(photos, locations, assignment?.id?.value, totalHours)
        }
    }
}

