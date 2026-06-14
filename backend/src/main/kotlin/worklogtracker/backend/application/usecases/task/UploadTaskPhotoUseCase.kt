package worklogtracker.backend.application.usecases.task

import worklogtracker.backend.domain.entities.TaskPhotoEntity
import worklogtracker.backend.domain.repositories.TaskPhotoRepositoryInterface
import worklogtracker.backend.domain.valueobjects.task.TaskId
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

class UploadTaskPhotoUseCase(
    private val taskPhotoRepository: TaskPhotoRepositoryInterface,
) {
    suspend operator fun invoke(
        userId: UserId,
        taskId: TaskId,
        photoUrl: String,
    ): Boolean {
        val photo =
            TaskPhotoEntity(
                taskId = taskId,
                photoUrl = photoUrl,
                uploadedBy = userId,
                uploadedAt = LocalDateTime.now(),
            )
        taskPhotoRepository.save(photo)
        return true
    }
}
