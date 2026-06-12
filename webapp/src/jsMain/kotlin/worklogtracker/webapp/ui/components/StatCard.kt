package worklogtracker.webapp.ui.components

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.webapp.ui.Styles

@Composable
fun StatCard(title: String, value: String, accentColor: CSSColorValue) {
    Div({
        style {
            backgroundColor(Styles.Surface)
            padding(24.px)
            borderRadius(12.px)
            property("box-shadow", "0 1px 3px rgba(0,0,0,0.1)")
            property("border-left", "4px solid $accentColor")
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
