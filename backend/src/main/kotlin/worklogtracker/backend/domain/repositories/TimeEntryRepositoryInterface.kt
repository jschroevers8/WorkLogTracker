package worklogtracker.backend.domain.repositories

import worklogtracker.backend.domain.entities.TimeEntryEntity
import worklogtracker.backend.domain.valueobjects.task.TaskAssignmentId
import worklogtracker.backend.domain.valueobjects.user.UserId
import worklogtracker.backend.domain.valueobjects.worklog.TimeEntryId

interface TimeEntryRepositoryInterface {
    suspend fun findById(id: TimeEntryId): TimeEntryEntity?
    suspend fun save(timeEntry: TimeEntryEntity): TimeEntryEntity
    suspend fun findByAssignment(taskAssignmentId: TaskAssignmentId): List<TimeEntryEntity>
    suspend fun findByUser(userId: UserId): List<TimeEntryEntity>
}
