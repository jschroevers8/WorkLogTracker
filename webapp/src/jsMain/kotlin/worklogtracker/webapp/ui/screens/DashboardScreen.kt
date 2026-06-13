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
        }
    }) {
        StatCard("Totaal Medewerkers", viewModel.totalEmployees.toString(), Styles.Primary)
        StatCard("Actieve Projecten", viewModel.activeProjects.toString(), Styles.Success)
        StatCard("Uren deze week", viewModel.totalHoursThisWeek.toString(), Styles.Accent)
    }
}

