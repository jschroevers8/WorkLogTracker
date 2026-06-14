package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import worklogtracker.shared.dto.task.TaskResponse
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.viewmodel.ProjectDetailViewModel
import kotlin.js.Date

@Composable
fun ProjectDetailScreen(
    projectId: Int,
    onBack: () -> Unit,
) {
    val viewModel = koinInject<ProjectDetailViewModel> { parametersOf(projectId) }

    LaunchedEffect(Unit) {
        viewModel.loadProjectDetails()
    }

    Div({
        style {
            marginBottom(24.px)
        }
    }) {
        Button({
            onClick { onBack() }
            style {
                padding(8.px, 16.px)
                backgroundColor(Color.transparent)
                color(Styles.Primary)
                border(1.px, LineStyle.Solid, Styles.Primary)
                borderRadius(6.px)
                cursor("pointer")
                marginBottom(16.px)
                fontWeight("600")
            }
        }) { Text("← Terug naar Projecten") }

        if (viewModel.loading) {
            P { Text("Project details laden...") }
        } else if (viewModel.error.isNotEmpty()) {
            P({ style { color(Styles.Error) } }) { Text(viewModel.error) }
        } else {
            viewModel.project?.let { p ->
                H2 { Text("Project: ${p.name}") }
                P({
                    style {
                        color(Styles.TextSecondary)
                        marginBottom(24.px)
                    }
                }) {
                    Text(p.description ?: "Geen beschrijving beschikbaar.")
                }

                H3 { Text("Taken") }

                if (viewModel.tasks.isEmpty()) {
                    P { Text("Geen taken gevonden voor dit project.") }
                } else {
                    viewModel.tasks.forEach { task ->
                        TaskDetailCard(task)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskDetailCard(task: TaskResponse) {
    Div({
        style {
            backgroundColor(Styles.Surface)
            padding(24.px)
            borderRadius(12.px)
            marginBottom(20.px)
            border(1.px, LineStyle.Solid, Styles.Border)
            property("box-shadow", "0 1px 2px rgba(0,0,0,0.05)")
        }
    }) {
        Div({
            style {
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.SpaceBetween)
                alignItems(AlignItems.Center)
                marginBottom(12.px)
            }
        }) {
            H4({
                style {
                    margin(0.px)
                    color(Styles.TextPrimary)
                }
            }) { Text(task.title) }
            Div({
                style {
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                    gap(12.px)
                }
            }) {
                Span({
                    style {
                        fontSize(0.9.em)
                        fontWeight("600")
                        color(Styles.Primary)
                        backgroundColor(Color("#EFF6FF"))
                        padding(4.px, 10.px)
                        borderRadius(6.px)
                    }
                }) { Text("${task.totalHours} uur") }
                Span({
                    style {
                        padding(4.px, 12.px)
                        borderRadius(20.px)
                        fontSize(0.8.em)
                        fontWeight("600")
                        backgroundColor(
                            when (task.status) {
                                "COMPLETED" -> Color("#DEF7EC")
                                "IN_PROGRESS" -> Color("#E1EFFE")
                                else -> Color("#F3F4F6")
                            },
                        )
                        color(
                            when (task.status) {
                                "COMPLETED" -> Color("#03543F")
                                "IN_PROGRESS" -> Color("#1E429F")
                                else -> Color("#374151")
                            },
                        )
                    }
                }) { Text(task.status) }
            }
        }

        P({
            style {
                color(Styles.TextSecondary)
                fontSize(0.95.em)
                marginBottom(16.px)
            }
        }) {
            Text(task.description ?: "Geen beschrijving.")
        }

        if (task.photoUrls.isNotEmpty()) {
            H5({ style { marginBottom(8.px) } }) { Text("Foto's") }
            Div({
                style {
                    display(DisplayStyle.Flex)
                    flexWrap(FlexWrap.Wrap)
                    gap(12.px)
                    marginBottom(16.px)
                }
            }) {
                task.photoUrls.forEach { url ->
                    Img(src = url, alt = "Task foto") {
                        style {
                            width(150.px)
                            height(150.px)
                            property("object-fit", "cover")
                            borderRadius(8.px)
                            border(1.px, LineStyle.Solid, Styles.Border)
                        }
                    }
                }
            }
        }

        if (task.locations.isNotEmpty()) {
            H5({ style { marginBottom(8.px) } }) { Text("Locaties") }
            Div({
                style {
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                    gap(8.px)
                }
            }) {
                task.locations.forEach { location ->
                    Div({
                        style {
                            fontSize(0.9.em)
                            color(Styles.TextPrimary)
                            display(DisplayStyle.Flex)
                            alignItems(AlignItems.Center)
                            gap(8.px)
                            padding(8.px)
                            backgroundColor(Color("#F9FAFB"))
                            borderRadius(6.px)
                        }
                    }) {
                        val date = Date(location.recordedAt)

                        val formatted =
                            "${date.getDate().toString().padStart(2, '0')}-" +
                                "${(date.getMonth() + 1).toString().padStart(2, '0')}-" +
                                "${date.getFullYear()} " +
                                "${date.getHours().toString().padStart(2, '0')}:" +
                                date.getMinutes().toString().padStart(2, '0')

                        Text("📍 ${location.latitude}, ${location.longitude} ($formatted)")
                        A(href = "https://www.google.com/maps?q=${location.latitude},${location.longitude}", attrs = {
                            attr("target", "_blank")
                            style {
                                color(Styles.Primary)
                                fontWeight("600")
                            }
                        }) {
                            Text("Kaart")
                        }
                    }
                }
            }
        }

        if (task.photoUrls.isEmpty() && task.locations.isEmpty()) {
            P({
                style {
                    fontSize(0.85.em)
                    color(Styles.TextSecondary)
                    fontStyle("italic")
                }
            }) {
                Text("Geen foto's of locaties beschikbaar voor deze taak.")
            }
        }
    }
}
