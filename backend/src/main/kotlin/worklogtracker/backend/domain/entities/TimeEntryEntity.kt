package worklogtracker.backend.domain.entities

import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.domain.valueobjects.worklog.TimeEntryId
import java.math.BigDecimal
import java.time.LocalDateTime

data class TimeEntryEntity(
    val id: TimeEntryId? = null,
    val taskAssignmentId: TaskAssignmentId,
    val userId: UserId,
    val hours: BigDecimal,
    val description: String?,
    val aiDescription: String? = null,
    val createdAt: LocalDateTime,
)
