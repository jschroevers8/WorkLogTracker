package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.webapp.ApiClient

class ProjectDetailViewModel(
    private val api: ApiClient,
) {
    var project by mutableStateOf<ProjectResponse?>(null)
    var tasks by mutableStateOf<List<TaskResponse>>(emptyList())
    var loading by mutableStateOf(true)
    var error by mutableStateOf("")

    suspend fun loadProjectDetails(projectId: Int) {
        try {
            loading = true
            error = ""
            val projects = api.projects.getProjects()
            project = projects.find { it.id == projectId }

            if (project != null) {
                tasks = api.tasks.getTasks(projectId = projectId)
            } else {
                error = "Project niet gevonden"
            }
        } catch (e: Exception) {
            error = "Fout bij laden project details: ${e.message}"
        } finally {
            loading = false
        }
    }
}
