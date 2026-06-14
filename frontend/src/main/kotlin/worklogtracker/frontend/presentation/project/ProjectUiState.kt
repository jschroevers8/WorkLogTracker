package worklogtracker.frontend.presentation.project

import worklogtracker.frontend.presentation.framework.viewmodel.BaseUiState
import worklogtracker.frontend.presentation.project.item.ProjectItem

data class ProjectUiState(
    val projects: List<ProjectItem> = emptyList(),
    override val loading: Boolean = false,
    override val error: String? = null,
) : BaseUiState
