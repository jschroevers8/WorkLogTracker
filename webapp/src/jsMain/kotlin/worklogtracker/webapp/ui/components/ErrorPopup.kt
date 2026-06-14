package worklogtracker.webapp.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import worklogtracker.webapp.ui.Styles

@Composable
fun ErrorPopup(message: String, onClose: () -> Unit) {
    if (message.isEmpty()) return

    Div({
        style {
            position(Position.Fixed)
            top(0.px)
            left(0.px)
            width(100.percent)
            height(100.percent)
            backgroundColor(Color("rgba(0, 0, 0, 0.5)"))
            display(DisplayStyle.Flex)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.Center)
            property("z-index", 1000)
        }
        onClick { onClose() }
    }) {
        Div({
            style {
                backgroundColor(Styles.Surface)
                padding(24.px)
                borderRadius(12.px)
                maxWidth(400.px)
                width(90.percent)
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                alignItems(AlignItems.Center)
                property("box-shadow", "0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)")
            }
            onClick { it.stopPropagation() }
        }) {
            H3({
                style {
                    color(Styles.Error)
                    marginTop(0.px)
                }
            }) { Text("Melding") }

            P({
                style {
                    textAlign("center")
                    marginBottom(24.px)
                    color(Styles.TextPrimary)
                }
            }) { Text(message) }

            Button({
                onClick { onClose() }
                style {
                    padding(10.px, 24.px)
                    backgroundColor(Styles.Primary)
                    color(Color.white)
                    border(0.px)
                    borderRadius(8.px)
                    cursor("pointer")
                    fontWeight("600")
                }
            }) { Text("Oké") }
        }
    }
}
