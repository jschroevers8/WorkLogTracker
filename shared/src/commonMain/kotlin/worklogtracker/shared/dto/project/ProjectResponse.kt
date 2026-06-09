package worklogtracker.shared.dto.project

import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    val id: Int?,
    val name: String,
    val description: String?,
    val status: String,
    val startDate: String?,
    val endDate: String?,
    val createdAt: String
)
