package worklogtracker.shared.dto.project

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProjectRequest(
    val name: String? = null,
    val description: String? = null,
    val status: String? = null,
)
