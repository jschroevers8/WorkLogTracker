package worklogtracker.backend.domain.factories

import worklogtracker.backend.domain.entities.WorkLogEntity
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WorkLogFactory {
    fun create(
        taskId: TaskId,
        userId: UserId,
        startTime: LocalDateTime,
        endTime: LocalDateTime? = null,
        notes: String? = null,
        photoUrl: String? = null,
        latitude: Double? = null,
        longitude: Double? = null,
    ): WorkLogEntity {
        val durationMinutes =
            if (endTime != null) {
                ChronoUnit.MINUTES.between(startTime, endTime).toInt()
            } else {
                null
            }

        return WorkLogEntity(
            taskId = taskId,
            userId = userId,
            startTime = startTime,
            endTime = endTime,
            durationMinutes = durationMinutes,
            notes = notes,
            photoUrl = photoUrl,
            latitude = latitude,
            longitude = longitude,
            isSynced = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
    }
}
