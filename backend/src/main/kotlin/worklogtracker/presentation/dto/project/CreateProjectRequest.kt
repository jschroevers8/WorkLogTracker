package worklogtracker.presentation.dto.project

import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectRequest(
    val name: String,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
)
