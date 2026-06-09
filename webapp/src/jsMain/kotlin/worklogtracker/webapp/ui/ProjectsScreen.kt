package worklogtracker.webapp.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.webapp.ApiClient
import kotlinx.coroutines.launch

@Composable
fun ProjectsScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope) {
    var projects by remember { mutableStateOf<List<ProjectResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            projects = api.getProjects()
        } catch (e: Exception) {
            error = "Fout bij ophalen projecten: ${e.message}"
        } finally {
            loading = false
        }
    }

    H2({
        style {
            color(Styles.TextPrimary)
            marginBottom(24.px)
        }
    }) { Text("Projecten") }

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
                ProjectCard(project)
            }
        }
    }
}

@Composable
fun ProjectCard(project: ProjectResponse) {
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
                justifyContent(JustifyContent.SpaceBetween)
                alignItems(AlignItems.Center)
            }
        }) {
            Span({
                style {
                    fontSize(0.8.em)
                    color(Styles.Primary)
                    fontWeight("600")
                }
            }) { Text("ID: ${project.id}") }
            
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
