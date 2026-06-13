package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.shared.dto.project.CreateProjectRequest
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.webapp.ApiClient

class ProjectsViewModel(
    private val api: ApiClient,
    private val scope: CoroutineScope
) {
    var activeProjects by mutableStateOf<List<ProjectResponse>>(emptyList())
    var completedProjects by mutableStateOf<List<ProjectResponse>>(emptyList())
    var selectedTab by mutableStateOf("ACTIVE")
    var users by mutableStateOf<List<UserResponse>>(emptyList())
    var loading by mutableStateOf(true)
    var error by mutableStateOf("")

    var showCreateProject by mutableStateOf(false)
    var newProjectName by mutableStateOf("")
    var newProjectDesc by mutableStateOf("")

    var selectedProjectIdForTask by mutableStateOf<Int?>(null)
    var newTaskTitle by mutableStateOf("")
    var newTaskDesc by mutableStateOf("")
    var selectedUserIdForTask by mutableStateOf<Int?>(null)

    suspend fun refreshData() {
        try {
            activeProjects = api.projects.getProjects(excludeStatus = "COMPLETED")
            completedProjects = api.projects.getProjects(status = "COMPLETED")
            users = api.users.getUsers()
            error = ""
        } catch (e: Exception) {
            error = "Fout bij ophalen data: ${e.message}"
        } finally {
            loading = false
        }
    }

    fun toggleCreateProject() {
        showCreateProject = !showCreateProject
    }

    fun createProject() {
        scope.launch {
            try {
                api.projects.createProject(CreateProjectRequest(newProjectName, newProjectDesc))
                refreshData()
                showCreateProject = false
                newProjectName = ""
                newProjectDesc = ""
            } catch (e: Exception) {
                error = "Fout bij aanmaken project: ${e.message}"
            }
        }
    }

    fun createTask() {
        val currentProjectId = selectedProjectIdForTask
        val currentUserId = selectedUserIdForTask
        if (currentProjectId == null || currentUserId == null) return
        
        val pid: Int = currentProjectId
        val uid: Int = currentUserId

        scope.launch {
            try {
                val task = api.tasks.createTask(CreateTaskRequest(pid, newTaskTitle, newTaskDesc, uid))
                val request = AssignTaskRequest(uid)
                api.tasks.assignTask(task.id, request)
                refreshData()
                selectedProjectIdForTask = null
                newTaskTitle = ""
                newTaskDesc = ""
                selectedUserIdForTask = null
            } catch (e: Exception) {
                error = "Fout bij aanmaken/toewijzen taak: ${e.message}"
            }
        }
    }

    private suspend fun performAssignTask(taskId: Int, userId: Int) {
        api.tasks.assignTask(taskId, AssignTaskRequest(userId))
    }
}
