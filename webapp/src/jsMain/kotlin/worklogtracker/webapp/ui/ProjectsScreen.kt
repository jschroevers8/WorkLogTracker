package worklogtracker.webapp.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.shared.dto.project.CreateProjectRequest
import worklogtracker.shared.dto.task.CreateTaskRequest
import worklogtracker.shared.dto.task.AssignTaskRequest
import worklogtracker.shared.dto.user.UserResponse
import worklogtracker.webapp.ApiClient
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.selected

@Composable
fun ProjectsScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope) {
    var projects by remember { mutableStateOf<List<ProjectResponse>>(emptyList()) }
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

    LaunchedEffect(Unit) {
        try {
            projects = api.getProjects()
            users = api.getUsers()
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
                            api.createProject(CreateProjectRequest(newProjectName, newProjectDesc))
                            projects = api.getProjects()
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
                            val taskResponse = api.createTask(CreateTaskRequest(
                                projectId = pid,
                                title = newTaskTitle,
                                description = newTaskDesc,
                                estimatedHours = 8.0,
                                priority = "MEDIUM"
                            ))
                            
                            val taskId = taskResponse.id
                            if (taskId != null && selectedUserIdForTask != null) {
                                api.assignTask(taskId, AssignTaskRequest(selectedUserIdForTask!!))
                            }

                            projects = api.getProjects()
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

    if (loading) {
        P { Text("Laden...") }
    } else if (error.isNotEmpty()) {
        P({ style { color(Styles.Error) } }) { Text(error) }
    } else {
        Div({
            style {
                display(DisplayStyle.Grid)
                gridTemplateColumns("repeat(auto-fill, minmax(300px, 1fr))")
                gap(20.px)
            }
        }) {
            projects.forEach { project ->
                ProjectCard(project, onAddTask = { selectedProjectIdForTask = project.id })
            }
        }
    }
}

@Composable
fun ProjectCard(project: ProjectResponse, onAddTask: () -> Unit) {
    Div({
        style {
            backgroundColor(Styles.Surface)
            padding(20.px)
            borderRadius(12.px)
            property("box-shadow", "0 1px 3px rgba(0,0,0,0.1)")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
        }
    }) {
        H3({
            style {
                margin(0.px)
                color(Styles.TextPrimary)
                marginBottom(8.px)
            }
        }) { Text(project.name) }
        
        P({
            style {
                color(Styles.TextSecondary)
                fontSize(0.9.em)
                margin(0.px)
                flex(1)
            }
        }) { Text(project.description ?: "Geen beschrijving") }

        Div({
            style {
                marginTop(16.px)
                display(DisplayStyle.Flex)
                gap(8.px)
                alignItems(AlignItems.Center)
            }
        }) {
            Button({
                onClick { onAddTask() }
                style {
                    padding(6.px, 12.px)
                    backgroundColor(Styles.Accent)
                    color(Color.white)
                    border(0.px)
                    borderRadius(6.px)
                    cursor("pointer")
                    fontSize(0.8.em)
                }
            }) { Text("+ Taak") }
            
            Button({
                style {
                    padding(6.px, 12.px)
                    backgroundColor(Color.white)
                    color(Styles.Secondary)
                    border(1.px, LineStyle.Solid, Styles.Border)
                    borderRadius(6.px)
                    cursor("pointer")
                    fontSize(0.8.em)
                }
            }) { Text("Bewerken") }
        }
    }
}
