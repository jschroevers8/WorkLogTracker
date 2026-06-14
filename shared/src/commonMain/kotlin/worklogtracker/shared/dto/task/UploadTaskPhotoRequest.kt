package worklogtracker.shared.dto.task

import kotlinx.serialization.Serializable

@Serializable
data class UploadTaskPhotoRequest(
    val taskId: Int,
    val photoUrl: String,
)
