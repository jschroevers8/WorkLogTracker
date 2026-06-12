package worklogtracker.frontend.presentation.project

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState

data class ProjectUiState(
    val projects: List<ProjectItem> = emptyList(),
    override val loading: Boolean = false,
    override val error: String? = null
) : BaseUiState

data class ProjectItem(
    val id: String,
    val name: String,
    val description: String,
    val status: String
)
