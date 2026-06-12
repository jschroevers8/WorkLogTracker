package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.StatCard

@Composable
fun DashboardScreen() {
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
        StatCard("Totaal Medewerkers", "12", Styles.Primary)
        StatCard("Actieve Projecten", "5", Styles.Success)
        StatCard("Uren deze week", "156", Styles.Accent)
    }
}

