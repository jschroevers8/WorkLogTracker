package worklogtracker.backend.application.usecases.worklog

import worklogtracker.backend.domain.repositories.TimeEntryRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.shared.dto.worklog.WorkLogResponse

class GetUserWorkLogsUseCase(
    private val timeEntryRepository: TimeEntryRepositoryInterface,
) {
    suspend operator fun invoke(
        userId: UserId,
        taskAssignmentId: TaskAssignmentId? = null,
    ): List<WorkLogResponse> {
        val entries =
            if (taskAssignmentId != null) {
                timeEntryRepository.findByAssignment(taskAssignmentId)
            } else {
                timeEntryRepository.findByUser(userId)
            }
        return entries.map {
            WorkLogResponse(
                id = it.id?.value,
                taskAssignmentId = it.taskAssignmentId.value,
                userId = it.userId.value,
                hours = it.hours.toDouble(),
                description = it.description,
                createdAt = it.createdAt.toString(),
            )
        }
    }
}
