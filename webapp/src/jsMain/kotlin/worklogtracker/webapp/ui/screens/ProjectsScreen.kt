package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.shared.dto.project.CreateProjectRequest
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.ProjectCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.selected

@Composable
fun ProjectsScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onSeeProjectDetails: (Int) -> Unit) {
    var activeProjects by remember { mutableStateOf<List<ProjectResponse>>(emptyList()) }
    var completedProjects by remember { mutableStateOf<List<ProjectResponse>>(emptyList()) }
    var selectedTab by remember { mutableStateOf("ACTIVE") }
    var users by remember { mutableStateOf<List<UserResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }
    
    var showCreateProject by remember { mutableStateOf(false) }
    var newProjectName by remember { mutableStateOf("") }
    var newProjectDesc by remember { mutableStateOf("") }

    var selectedProjectIdForTask by remember { mutableStateOf<Int?>(null) }
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDesc by remember { mutableStateOf("") }
    var selectedUserIdForTask by remember { mutableStateOf<Int?>(null) }

    suspend fun refreshData() {
        activeProjects = api.projects.getProjects(excludeStatus = "COMPLETED")
        completedProjects = api.projects.getProjects(status = "COMPLETED")
        users = api.users.getUsers()
    }

    LaunchedEffect(Unit) {
        try {
            refreshData()
        } catch (e: Exception) {
            error = "Fout bij ophalen data: ${e.message}"
        } finally {
            loading = false
        }
    }

    Div({
        style {
            display(DisplayStyle.Flex)
            justifyContent(JustifyContent.SpaceBetween)
            alignItems(AlignItems.Center)
            marginBottom(24.px)
        }
    }) {
        H2({
            style {
                color(Styles.TextPrimary)
                margin(0.px)
            }
        }) { Text("Projecten & Beheer") }

        Button({
            onClick { showCreateProject = !showCreateProject }
            style {
                padding(10.px, 20.px)
                backgroundColor(Styles.Primary)
                color(Color.white)
                border(0.px)
                borderRadius(8.px)
                cursor("pointer")
                fontWeight("600")
            }
        }) { Text(if (showCreateProject) "Annuleren" else "+ Nieuw Project") }
    }

    if (showCreateProject) {
        Div({
            style {
                backgroundColor(Styles.Surface)
                padding(20.px)
                borderRadius(12.px)
                marginBottom(24.px)
                border(1.px, LineStyle.Solid, Styles.Border)
            }
        }) {
            H3 { Text("Nieuw Project Aanmaken") }
            Input(InputType.Text) {
                placeholder("Projectnaam")
                value(newProjectName)
                onInput { newProjectName = it.value }
                style { width(100.percent); padding(8.px); marginBottom(8.px); borderRadius(6.px); border(1.px, LineStyle.Solid, Styles.Border) }
            }
            TextArea {
                placeholder("Beschrijving")
                value(newProjectDesc)
                onInput { newProjectDesc = it.value }
                style { width(100.percent); padding(8.px); marginBottom(16.px); borderRadius(6.px); border(1.px, LineStyle.Solid, Styles.Border); minHeight(60.px) }
            }
            Button({
                onClick {
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
                style { padding(8.px, 16.px); backgroundColor(Styles.Success); color(Color.white); border(0.px); borderRadius(6.px); cursor("pointer") }
            }) { Text("Project Opslaan") }
        }
    }

    if (selectedProjectIdForTask != null) {
        Div({
            style {
                backgroundColor(Styles.Surface)
                padding(20.px)
                borderRadius(12.px)
                marginBottom(24.px)
                border(1.px, LineStyle.Solid, Styles.Border)
            }
        }) {
            H3 { Text("Nieuwe Taak voor Project ID: $selectedProjectIdForTask") }
            Input(InputType.Text) {
                placeholder("Taak Titel")
                value(newTaskTitle)
                onInput { newTaskTitle = it.value }
                style { width(100.percent); padding(8.px); marginBottom(8.px); borderRadius(6.px); border(1.px, LineStyle.Solid, Styles.Border) }
            }
            TextArea {
                placeholder("Beschrijving")
                value(newTaskDesc)
                onInput { newTaskDesc = it.value }
                style { width(100.percent); padding(8.px); marginBottom(16.px); borderRadius(6.px); border(1.px, LineStyle.Solid, Styles.Border); minHeight(60.px) }
            }

            H4 { Text("Toewijzen aan medewerker") }
            Select({
                onInput { selectedUserIdForTask = it.value?.toIntOrNull() }
                style { width(100.percent); padding(8.px); marginBottom(16.px); borderRadius(6.px); border(1.px, LineStyle.Solid, Styles.Border) }
            }) {
                Option("", {
                    if (selectedUserIdForTask == null) selected()
                }) {
                    Text("Selecteer een medewerker")
                }

                users.forEach { user ->
                    Option(user.id.toString(), {
                        if (selectedUserIdForTask?.toLong() == user.id) selected()
                    }) {
                        Text("${user.firstName} ${user.lastName}")
                    }
                }
            }

            Button({
                onClick {
                    scope.launch {
                        try {
                            val pid = selectedProjectIdForTask!!
                            api.tasks.createTask(CreateTaskRequest(
                                projectId = pid,
                                title = newTaskTitle,
                                description = newTaskDesc,
                                assignedUserId = selectedUserIdForTask ?: 0
                            ))
                            
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
                style { padding(8.px, 16.px); backgroundColor(Styles.Success); color(Color.white); border(0.px); borderRadius(6.px); cursor("pointer") }
            }) { Text("Taak Opslaan & Toewijzen") }
            Button({
                onClick { selectedProjectIdForTask = null }
                style { marginLeft(8.px); padding(8.px, 16.px); backgroundColor(Styles.Secondary); color(Color.white); border(0.px); borderRadius(6.px); cursor("pointer") }
            }) { Text("Annuleren") }
        }
    }

    Div({
        style {
            display(DisplayStyle.Flex)
            gap(16.px)
            marginBottom(24.px)
            property("border-bottom", "1px solid ${Styles.Border}")
        }
    }) {
        Div({
            onClick { selectedTab = "ACTIVE" }
            style {
                padding(12.px, 24.px)
                cursor("pointer")
                if (selectedTab == "ACTIVE") {
                    property("border-bottom", "3px solid ${Styles.Primary}")
                    fontWeight("bold")
                }
            }
        }) { Text("Actieve Projecten") }

        Div({
            onClick { selectedTab = "COMPLETED" }
            style {
                padding(12.px, 24.px)
                cursor("pointer")
                if (selectedTab == "COMPLETED") {
                    property("border-bottom", "3px solid ${Styles.Primary}")
                    fontWeight("bold")
                }
            }
        }) { Text("Voltooide Projecten") }
    }

    if (loading) {
        P { Text("Laden...") }
    } else if (error.isNotEmpty()) {
        P({ style { color(Styles.Error) } }) { Text(error) }
    } else {
        val currentProjects = if (selectedTab == "ACTIVE") activeProjects else completedProjects

        Div({
            style {
                display(DisplayStyle.Grid)
                gridTemplateColumns("repeat(auto-fill, minmax(300px, 1fr))")
                gap(20.px)
            }
        }) {
            currentProjects.forEach { project ->
                ProjectCard(
                    project = project,
                    onAddTask = { selectedProjectIdForTask = project.id },
                    onSeeDetails = { onSeeProjectDetails(project.id!!) }
                )
            }
        }
    }
}

