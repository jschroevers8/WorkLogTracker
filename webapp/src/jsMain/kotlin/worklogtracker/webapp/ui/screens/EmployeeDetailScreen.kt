package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import worklogtracker.webapp.ui.Styles
import worklogtracker.webapp.viewmodel.EmployeeDetailViewModel
import kotlin.js.Date

@Composable
fun EmployeeDetailScreen(
    userId: Long,
    onBack: () -> Unit,
) {
    val viewModel = koinInject<EmployeeDetailViewModel> { parametersOf(userId) }

    LaunchedEffect(Unit) {
        viewModel.loadWorkLogs()
    }

    Div({
        style {
            display(DisplayStyle.Flex)
            alignItems(AlignItems.Center)
            marginBottom(24.px)
        }
    }) {
        Button({
            style {
                padding(8.px, 16.px)
                backgroundColor(Color.white)
                color(Styles.Primary)
                border(1.px, LineStyle.Solid, Styles.Primary)
                borderRadius(8.px)
                cursor("pointer")
                marginRight(16.px)
                fontWeight("500")
            }
            onClick { onBack() }
        }) { Text("← Terug") }
        H2({
            style {
                margin(0.px)
                color(Styles.TextPrimary)
            }
        }) { Text("Urenoverzicht Medewerker #$userId") }
    }

    if (viewModel.loading) {
        P { Text("Laden...") }
    } else if (viewModel.error.isNotEmpty()) {
        P({ style { color(Styles.Error) } }) { Text(viewModel.error) }
    } else if (viewModel.worklogs.isEmpty()) {
        Div({
            style {
                padding(40.px)
                textAlign("center")
                backgroundColor(Styles.Surface)
                borderRadius(12.px)
                color(Styles.TextSecondary)
            }
        }) {
            Text("Geen gewerkte uren gevonden voor deze medewerker.")
        }
    } else {
        Div({
            style {
                backgroundColor(Styles.Surface)
                borderRadius(12.px)
                property("box-shadow", "0 1px 3px rgba(0,0,0,0.1)")
                overflow("hidden")
            }
        }) {
            Table({
                style {
                    width(100.percent)
                    property("border-collapse", "collapse")
                }
            }) {
                Thead {
                    Tr({
                        style {
                            backgroundColor(Color("#F8FAFC"))
                            property("border-bottom", "1px solid ${Styles.Border}")
                        }
                    }) {
                        Th({
                            style {
                                textAlign("left")
                                padding(16.px)
                                color(Styles.TextSecondary)
                                fontWeight("600")
                            }
                        }) { Text("Datum") }
                        Th({
                            style {
                                textAlign("left")
                                padding(16.px)
                                color(Styles.TextSecondary)
                                fontWeight("600")
                            }
                        }) { Text("Uren") }
                        Th({
                            style {
                                textAlign("left")
                                padding(16.px)
                                color(Styles.TextSecondary)
                                fontWeight("600")
                            }
                        }) { Text("Beschrijving") }
                    }
                }
                Tbody {
                    viewModel.worklogs.forEach { log ->
                        Tr({
                            style {
                                property("border-bottom", "1px solid ${Styles.Border}")
                            }
                        }) {
                            Td({
                                style {
                                    padding(16.px)
                                    color(Styles.TextPrimary)
                                }
                            }) {
                                val logDate = Date(log.createdAt)

                                val formatted =
                                    "${logDate.getDate().toString().padStart(2, '0')}-" +
                                        "${(logDate.getMonth() + 1).toString().padStart(2, '0')}-" +
                                        "${logDate.getFullYear()} " +
                                        "${logDate.getHours().toString().padStart(2, '0')}:" +
                                        logDate.getMinutes().toString().padStart(2, '0')

                                Text(formatted)
                            }
                            Td({
                                style {
                                    padding(16.px)
                                    color(Styles.TextPrimary)
                                }
                            }) {
                                Span({
                                    style {
                                        fontWeight("600")
                                    }
                                }) { Text(log.hours.toString()) }
                            }
                            Td({
                                style {
                                    padding(16.px)
                                    color(Styles.TextSecondary)
                                }
                            }) { Text(log.description ?: "") }
                        }
                    }
                }
            }
        }
    }
}
