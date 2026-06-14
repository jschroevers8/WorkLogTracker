package worklogtracker.webapp.ui.components

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import worklogtracker.webapp.ui.Styles

@Composable
fun NavLink(
    text: String,
    active: Boolean,
    onClick: () -> Unit,
) {
    Div({
        style {
            marginRight(24.px)
            cursor("pointer")
            padding(20.px, 0.px)
            fontSize(0.95.em)
            fontWeight("500")
            color(if (active) Styles.Primary else Styles.TextSecondary)
            if (active) {
                property("border-bottom", "2px solid ${Styles.Primary}")
            }
            property("transition", "color 0.2s, border-bottom 0.2s")
        }
        onClick { onClick() }
    }) { Text(text) }
}
