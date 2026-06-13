package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.StatCard
import worklogtracker.webapp.viewmodel.DashboardViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import worklogtracker.webapp.ApiClient

@Composable
fun DashboardScreen(apiClient: ApiClient, scope: CoroutineScope) {
    val viewModel = remember { DashboardViewModel(apiClient) }

    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }

    H2({
        style {
            color(Styles.TextPrimary)
            marginBottom(24.px)
        }
    }) { Text("Dashboard") }

    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateColumns("repeat(auto-fit, minmax(240px, 1fr))")
            gap(24.px)
            marginBottom(40.px)
        }
    }) {
        StatCard("Totaal Medewerkers", viewModel.totalEmployees.toString(), Styles.Primary)
        StatCard("Actieve Projecten", viewModel.activeProjects.toString(), Styles.Success)
        StatCard("Uren deze week", viewModel.totalHoursThisWeek.toString(), Styles.Accent)
    }

    Div({
        style {
            backgroundColor(Styles.Surface)
            padding(24.px)
            borderRadius(12.px)
            border(1.px, LineStyle.Solid, Styles.Border)
            property("box-shadow", "0 1px 3px 0 rgba(0, 0, 0, 0.1)")
        }
    }) {
        H3({ style { marginBottom(24.px); color(Styles.TextPrimary) } }) { Text("Gewerkte uren per dag") }

        Div({
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.FlexEnd)
                justifyContent(JustifyContent.SpaceAround)
                height(200.px)
                padding(0.px, 20.px)
                border(0.px, LineStyle.Solid, Styles.Border)
                property("border-bottom", "2px solid ${Styles.Border}")
            }
        }) {
            viewModel.dailyHours.forEach { (day, hours) ->
                Div({
                    style {
                        display(DisplayStyle.Flex)
                        flexDirection(FlexDirection.Column)
                        alignItems(AlignItems.Center)
                        width(40.px)
                    }
                }) {
                    Div({
                        style {
                            width(100.percent)
                            height((hours * 15).px) // Schaal uren naar pixels
                            backgroundColor(Styles.Primary)
                            borderRadius(4.px, 4.px, 0.px, 0.px)
                            property("transition", "height 0.5s ease-in-out")
                        }
                        title("$hours uur")
                    })
                    Span({
                        style {
                            marginTop(8.px)
                            fontSize(0.8.em)
                            color(Styles.TextSecondary)
                            fontWeight("600")
                        }
                    }) { Text(day) }
                }
            }
        }
    }
}

