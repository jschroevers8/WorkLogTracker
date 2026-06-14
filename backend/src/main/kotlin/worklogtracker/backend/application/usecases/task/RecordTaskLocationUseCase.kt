package worklogtracker.backend.application.usecases.task

import worklogtracker.backend.domain.entities.TaskLocationEntity
import worklogtracker.backend.domain.repositories.TaskLocationRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import java.time.LocalDateTime

class RecordTaskLocationUseCase(
    private val taskLocationRepository: TaskLocationRepositoryInterface,
) {
    suspend operator fun invoke(
        taskId: TaskId,
        latitude: Double,
        longitude: Double,
    ): Boolean {
        val location =
            TaskLocationEntity(
                taskId = taskId,
                latitude = latitude,
                longitude = longitude,
                recordedAt = LocalDateTime.now(),
            )
        taskLocationRepository.save(location)
        return true
    }
}
