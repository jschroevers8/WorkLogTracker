package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class UploadTaskPhotoRequest(
    val taskId: Int,
    val photoUrl: String // In reality, this might be multipart, but following simpler pattern for now
)
