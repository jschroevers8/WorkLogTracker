package worklogtracker.backend.domain.entities

import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.task.TaskLocationId
import java.time.LocalDateTime

data class TaskLocationEntity(
    val id: TaskLocationId? = null,
    val taskId: TaskId,
    val latitude: Double,
    val longitude: Double,
    val recordedAt: LocalDateTime,
)
