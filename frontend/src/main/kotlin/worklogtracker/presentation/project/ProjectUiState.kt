package worklogtracker.presentation.project

data class ProjectUiState(
    val projects: List<ProjectItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

data class ProjectItem(
    val id: String,
    val name: String,
    val description: String,
    val status: String
)
