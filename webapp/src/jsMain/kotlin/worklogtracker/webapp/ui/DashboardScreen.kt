package worklogtracker.webapp.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*

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

@Composable
fun StatCard(title: String, value: String, accentColor: CSSColorValue) {
    Div({
        style {
            backgroundColor(Styles.Surface)
            padding(24.px)
            borderRadius(12.px)
//            boxShadow("0 1px 3px rgba(0,0,0,0.1)")
//            borderLeft(4.px, LineStyle.Solid, accentColor)
        }
    }) {
        Div({
            style {
                color(Styles.TextSecondary)
                fontSize(0.9.em)
                marginBottom(8.px)
                fontWeight("500")
            }
        }) { Text(title) }
        Div({
            style {
                color(Styles.TextPrimary)
                fontSize(2.em)
                fontWeight("bold")
            }
        }) { Text(value) }
    }
}
