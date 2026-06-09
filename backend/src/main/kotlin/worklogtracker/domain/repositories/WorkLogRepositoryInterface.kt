package worklogtracker.domain.repositories

import worklogtracker.domain.entities.WorkLogEntity
import worklogtracker.domain.valueobjects.task.TaskId
import worklogtracker.domain.valueobjects.user.UserId
import worklogtracker.domain.valueobjects.worklog.WorkLogId
import java.time.LocalDateTime

interface WorkLogRepositoryInterface {
    suspend fun findById(id: WorkLogId): WorkLogEntity?
    suspend fun save(workLog: WorkLogEntity): WorkLogEntity
    suspend fun update(workLog: WorkLogEntity): Boolean
    suspend fun delete(id: WorkLogId): Boolean
    suspend fun findByTask(taskId: TaskId): List<WorkLogEntity>
    suspend fun findByUser(userId: UserId, from: LocalDateTime? = null, to: LocalDateTime? = null): List<WorkLogEntity>
    suspend fun findUnsyncedByUser(userId: UserId): List<WorkLogEntity>
}

