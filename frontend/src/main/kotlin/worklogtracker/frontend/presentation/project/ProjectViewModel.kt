package worklogtracker.frontend.presentation.project

import worklogtracker.frontend.presentation.framework.viewmodel.BaseViewModel
import worklogtracker.frontend.repositories.ProjectRepository

class ProjectViewModel(
    private val projectRepository: ProjectRepository
) : BaseViewModel<ProjectUiState>(ProjectUiState()) {
    fun loadProjects() {
        launchWithErrorHandling {
            val projects = projectRepository.getProjects()

            val projectList = projects.map { response ->
                ProjectItem(
                    id = response.id?.toString() ?: "",
                    name = response.name,
                    description = response.description ?: "",
                    status = response.status
                )
            }

            _uiState = uiState.copy(projects = projectList)
        }
    }

    override fun setLoading(value: Boolean) {
        _uiState = uiState.copy(loading = value)
    }

    override fun setError(message: String?) {
        _uiState = uiState.copy(error = message)
    }
}
