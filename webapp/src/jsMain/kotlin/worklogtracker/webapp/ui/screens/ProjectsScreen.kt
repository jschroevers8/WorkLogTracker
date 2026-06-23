package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import worklogtracker.shared.dto.project.UpdateProjectRequest
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.ErrorPopup
import worklogtracker.webapp.ui.components.ProjectCard
import worklogtracker.webapp.viewmodel.ProjectsViewModel

@Composable
fun ProjectsScreen(onSeeProjectDetails: (Int) -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel = koinInject<ProjectsViewModel> { parametersOf(scope) }

    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }

    ProjectsContent(
        loading = viewModel.loading,
        error = viewModel.error,
        showCreateProject = viewModel.showCreateProject,
        newProjectName = viewModel.newProjectName,
        newProjectDesc = viewModel.newProjectDesc,
        activeProjects = viewModel.activeProjects,
        onToggleCreateProject = { viewModel.toggleCreateProject() },
        onCreateProject = { viewModel.createProject() },
        onUpdateNewProjectName = { viewModel.newProjectName = it },
        onUpdateNewProjectDesc = { viewModel.newProjectDesc = it },
        onClearError = { viewModel.clearError() },
        onSeeProjectDetails = onSeeProjectDetails,
        viewModel = viewModel,
        scope = scope
    )
}

@Composable
fun ProjectsContent(
    loading: Boolean,
    error: String,
    showCreateProject: Boolean,
    newProjectName: String,
    newProjectDesc: String,
    activeProjects: List<worklogtracker.shared.dto.project.ProjectResponse>,
    onToggleCreateProject: () -> Unit,
    onCreateProject: () -> Unit,
    onUpdateNewProjectName: (String) -> Unit,
    onUpdateNewProjectDesc: (String) -> Unit,
    onClearError: () -> Unit,
    onSeeProjectDetails: (Int) -> Unit,
    viewModel: ProjectsViewModel,
    scope: kotlinx.coroutines.CoroutineScope
) {
    ErrorPopup(error) { onClearError() }

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
            id("toggle-create-project")
            onClick { onToggleCreateProject() }
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
            id("create-project-form")
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
                id("new-project-name")
                placeholder("Projectnaam")
                value(newProjectName)
                onInput { onUpdateNewProjectName(it.value) }
                style {
                    width(100.percent)
                    padding(8.px)
                    marginBottom(8.px)
                    borderRadius(6.px)
                    border(1.px, LineStyle.Solid, Styles.Border)
                }
            }
            TextArea {
                id("new-project-desc")
                placeholder("Beschrijving")
                value(newProjectDesc)
                onInput { onUpdateNewProjectDesc(it.value) }
                style {
                    width(100.percent)
                    padding(8.px)
                    marginBottom(16.px)
                    borderRadius(6.px)
                    border(1.px, LineStyle.Solid, Styles.Border)
                    minHeight(60.px)
                }
            }
            Button({
                id("save-project")
                onClick {
                    onCreateProject()
                }
                style {
                    padding(8.px, 16.px)
                    backgroundColor(Styles.Success)
                    color(Color.white)
                    border(0.px)
                    borderRadius(6.px)
                    cursor("pointer")
                }
            }) { Text("Project Opslaan") }
        }
    }

    if (viewModel.selectedProjectIdForTask != null) {
        Div({
            style {
                backgroundColor(Styles.Surface)
                padding(20.px)
                borderRadius(12.px)
                marginBottom(24.px)
                border(1.px, LineStyle.Solid, Styles.Border)
            }
        }) {
            H3 { Text("Nieuwe Taak voor Project ID: ${viewModel.selectedProjectIdForTask}") }
            Input(InputType.Text) {
                placeholder("Taak Titel")
                value(viewModel.newTaskTitle)
                onInput { viewModel.newTaskTitle = it.value }
                style {
                    width(100.percent)
                    padding(8.px)
                    marginBottom(8.px)
                    borderRadius(6.px)
                    border(1.px, LineStyle.Solid, Styles.Border)
                }
            }
            TextArea {
                placeholder("Beschrijving")
                value(viewModel.newTaskDesc)
                onInput { viewModel.newTaskDesc = it.value }
                style {
                    width(100.percent)
                    padding(8.px)
                    marginBottom(16.px)
                    borderRadius(6.px)
                    border(1.px, LineStyle.Solid, Styles.Border)
                    minHeight(60.px)
                }
            }

            H4 { Text("Toewijzen aan medewerker") }
            Select({
                onInput { viewModel.selectedUserIdForTask = it.value?.toIntOrNull() }
                style {
                    width(100.percent)
                    padding(8.px)
                    marginBottom(16.px)
                    borderRadius(6.px)
                    border(1.px, LineStyle.Solid, Styles.Border)
                }
            }) {
                Option("", {
                    if (viewModel.selectedUserIdForTask == null) selected()
                }) {
                    Text("Selecteer een medewerker")
                }

                viewModel.users.forEach { user ->
                    Option(user.id.toString(), {
                        if (viewModel.selectedUserIdForTask?.toLong() == user.id) selected()
                    }) {
                        Text("${user.firstName} ${user.lastName}")
                    }
                }
            }

            Button({
                onClick {
                    viewModel.createTask()
                }
                style {
                    padding(8.px, 16.px)
                    backgroundColor(Styles.Success)
                    color(Color.white)
                    border(0.px)
                    borderRadius(6.px)
                    cursor("pointer")
                }
            }) { Text("Taak Opslaan & Toewijzen") }
            Button({
                onClick { viewModel.selectedProjectIdForTask = null }
                style {
                    marginLeft(8.px)
                    padding(8.px, 16.px)
                    backgroundColor(Styles.Secondary)
                    color(Color.white)
                    border(0.px)
                    borderRadius(6.px)
                    cursor("pointer")
                }
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
            onClick { viewModel.selectedTab = "ACTIVE" }
            style {
                padding(12.px, 24.px)
                cursor("pointer")
                if (viewModel.selectedTab == "ACTIVE") {
                    property("border-bottom", "3px solid ${Styles.Primary}")
                    fontWeight("bold")
                }
            }
        }) { Text("Actieve Projecten") }

        Div({
            onClick { viewModel.selectedTab = "COMPLETED" }
            style {
                padding(12.px, 24.px)
                cursor("pointer")
                if (viewModel.selectedTab == "COMPLETED") {
                    property("border-bottom", "3px solid ${Styles.Primary}")
                    fontWeight("bold")
                }
            }
        }) { Text("Voltooide Projecten") }
    }

    if (viewModel.loading) {
        P { Text("Laden...") }
    } else {
        val currentProjects = if (viewModel.selectedTab == "ACTIVE") viewModel.activeProjects else viewModel.completedProjects

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
                    onAddTask = { viewModel.selectedProjectIdForTask = project.id },
                    onSeeDetails = { onSeeProjectDetails(project.id!!) },
                    onCloseProject = {
                        scope.launch {
                            try {
                                val tasks = viewModel.api.tasks.getTasks(projectId = project.id)
                                val hasUnfinishedTasks = tasks.any { it.status != "COMPLETED" }

                                if (hasUnfinishedTasks) {
                                    viewModel.error = "Kan project '${project.name}' niet afsluiten: er zijn nog onvoltooide taken."
                                    return@launch
                                }

                                viewModel.api.projects.updateProject(project.id!!, UpdateProjectRequest(status = "COMPLETED"))
                                viewModel.refreshData()
                            } catch (e: Exception) {
                                viewModel.error = "Fout bij afsluiten project: ${e.message}"
                            }
                        }
                    },
                )
            }
        }
    }
}
