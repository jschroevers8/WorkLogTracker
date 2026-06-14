package worklogtracker.webapp.navigation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.overflow
import org.jetbrains.compose.web.css.overflowY
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Text
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.ui.components.NavLink

@Composable
fun MainLayout(
    currentUser: AuthResponse?,
    onLogout: () -> Unit,
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    content: @Composable () -> Unit,
) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            height(100.vh)
            fontFamily("'Inter', 'Segoe UI', Arial, sans-serif")
            backgroundColor(Styles.Background)
        }
    }) {
        Nav({
            style {
                display(DisplayStyle.Flex)
                backgroundColor(Styles.Surface)
                color(Styles.TextPrimary)
                padding(0.px, 24.px)
                height(64.px)
                alignItems(AlignItems.Center)
                property("box-shadow", "0 1px 2px 0 rgba(0, 0, 0, 0.05)")
                property("z-index", 100)
            }
        }) {
            Div({
                style {
                    marginRight(40.px)
                    fontWeight("800")
                    cursor("pointer")
                    fontSize(1.25.em)
                    color(Styles.Primary)
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                    gap(8.px)
                }
                onClick { onNavigate(Screen.Dashboard) }
            }) {
                Img(src = "https://img.icons8.com/fluency/48/work.png") {
                    style {
                        width(24.px)
                        height(24.px)
                    }
                }
                Text("WLT Admin")
            }

            NavLink("Dashboard", currentScreen is Screen.Dashboard) { onNavigate(Screen.Dashboard) }
            NavLink("Medewerkers", currentScreen is Screen.Employees || currentScreen is Screen.EmployeeDetail) {
                onNavigate(Screen.Employees)
            }
            NavLink("Projecten", currentScreen is Screen.Projects || currentScreen is Screen.ProjectDetail) {
                onNavigate(Screen.Projects)
            }

            Div({
                style {
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                    property("margin-left", "auto")
                }
            }) {
                Div({
                    style {
                        marginRight(16.px)
                        textAlign("right")
                    }
                }) {
                    Div({
                        style {
                            fontWeight("600")
                            fontSize(0.9.em)
                            color(Styles.TextPrimary)
                        }
                    }) {
                        Text("${currentUser?.firstName} ${currentUser?.lastName}")
                    }
                    Div({
                        style {
                            fontSize(0.75.em)
                            color(Styles.TextSecondary)
                        }
                    }) {
                        Text(currentUser?.role ?: "")
                    }
                }

                Button({
                    style {
                        backgroundColor(Color.white)
                        color(Styles.Error)
                        border(1.px, LineStyle.Solid, Color("#FEE2E2"))
                        borderRadius(6.px)
                        padding(6.px, 12.px)
                        cursor("pointer")
                        fontSize(0.85.em)
                        fontWeight("500")
                    }
                    onClick { onLogout() }
                }) { Text("Log uit") }
            }
        }

        // Main Content Area
        Div({
            style {
                display(DisplayStyle.Flex)
                flex(1)
                overflow("hidden")
            }
        }) {
            Div({
                style {
                    padding(32.px)
                    flex(1)
                    overflowY("auto")
                }
            }) {
                content()
            }
        }
    }
}
