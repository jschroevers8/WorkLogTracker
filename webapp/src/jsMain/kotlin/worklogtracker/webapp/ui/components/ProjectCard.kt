package worklogtracker.webapp.ui.components

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.project.ProjectResponse
import worklogtracker.webapp.ui.Styles

@Composable
fun ProjectCard(project: ProjectResponse, onAddTask: () -> Unit, onSeeDetails: () -> Unit) {
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
                marginTop(8.px)
                fontSize(0.8.em)
                color(Styles.TextSecondary)
            }
        }) {
            Text("Status: ${project.status}")
        }

        Div({
            style {
                marginTop(16.px)
                display(DisplayStyle.Flex)
                gap(8.px)
                alignItems(AlignItems.Center)
            }
        }) {
            if (project.status != "COMPLETED") {
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
            }
            
            Button({
                onClick { onSeeDetails() }
                style {
                    padding(6.px, 12.px)
                    backgroundColor(Color.white)
                    color(Styles.Secondary)
                    border(1.px, LineStyle.Solid, Styles.Border)
                    borderRadius(6.px)
                    cursor("pointer")
                    fontSize(0.8.em)
                }
            }) { Text("Details") }
        }
    }
}
