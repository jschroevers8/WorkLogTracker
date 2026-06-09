package worklogtracker.application.usecases.worklog

import worklogtracker.application.dto.worklog.WorkLogResponse
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.repositories.WorkLogRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class GetUserWorkLogsUseCase(
    private val workLogRepository: WorkLogRepositoryInterface
) {
    
    suspend operator fun invoke(
        userId: UserId,
        from: LocalDateTime? = null,
        to: LocalDateTime? = null
    ): List<WorkLogResponse> {
        return workLogRepository.findByUser(userId, from, to).map { it.toResponse() }
    }
}

