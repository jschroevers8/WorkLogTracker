package worklogtracker.backend.application.usecases.worklog

import worklogtracker.backend.application.usecases.ai.GenerateAiDescriptionUseCase
import worklogtracker.backend.domain.entities.TimeEntryEntity
import worklogtracker.backend.domain.entities.enums.TaskStatus
import worklogtracker.backend.domain.repositories.TaskAssignmentRepositoryInterface
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.backend.domain.repositories.TimeEntryRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.math.BigDecimal
import java.time.LocalDateTime

class LogTimeUseCase(
    private val timeEntryRepository: TimeEntryRepositoryInterface,
    private val taskAssignmentRepository: TaskAssignmentRepositoryInterface,
    private val taskRepository: TaskRepositoryInterface,
    private val generateAiDescriptionUseCase: GenerateAiDescriptionUseCase
) {
    suspend operator fun invoke(
        userId: UserId,
        taskAssignmentId: TaskAssignmentId,
        hours: BigDecimal,
        description: String?
    ): Boolean {
        val assignment = taskAssignmentRepository.findById(taskAssignmentId)
            ?: throw Exception("Task assignment not found")

        val task = taskRepository.findById(assignment.taskId)
            ?: throw Exception("Task not found")

        val aiDescription = generateAiDescriptionUseCase(task.title, description)

        val timeEntry = TimeEntryEntity(
            taskAssignmentId = taskAssignmentId,
            userId = userId,
            hours = hours,
            description = description,
            aiDescription = aiDescription,
            createdAt = LocalDateTime.now()
        )
        timeEntryRepository.save(timeEntry)

        // Mark task as completed
        val updatedTask = task.updateStatus(TaskStatus.COMPLETED)
        taskRepository.update(updatedTask)

        // Also update assignment status if needed, though task status is primary
        val updatedAssignment = assignment.copy(status = TaskStatus.COMPLETED)
        taskAssignmentRepository.update(updatedAssignment)

        return true
    }
}
