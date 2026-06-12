package worklogtracker.backend.application.usecases.worklog

import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.backend.domain.repositories.TimeEntryRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class GetUserWorkLogsUseCase(
    private val timeEntryRepository: TimeEntryRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId
    ): List<WorkLogResponse> {
        return timeEntryRepository.findByUser(userId).map { 
            WorkLogResponse(
                id = it.id?.value,
                taskAssignmentId = it.taskAssignmentId.value,
                userId = it.userId.value,
                hours = it.hours.toDouble(),
                description = it.description,
                createdAt = it.createdAt.toString()
            )
        }
    }
}

