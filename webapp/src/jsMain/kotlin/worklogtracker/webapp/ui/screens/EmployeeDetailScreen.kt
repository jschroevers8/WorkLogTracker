package worklogtracker.webapp.ui.screens

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import worklogtracker.shared.dto.worklog.WorkLogResponse
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.ui.Styles
import kotlinx.coroutines.launch

@Composable
fun EmployeeDetailScreen(userId: Int, api: ApiClient, scope: kotlinx.coroutines.CoroutineScope, onBack: () -> Unit) {
    var worklogs by remember { mutableStateOf<List<WorkLogResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        try {
            worklogs = api.worklogs.getUserWorkLogs(userId)
        } catch (e: Exception) {
            error = "Fout bij ophalen uren: ${e.message}"
        } finally {
            loading = false
        }
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

    if (loading) {
        P { Text("Laden...") }
    } else if (error.isNotEmpty()) {
        P({ style { color(Styles.Error) } }) { Text(error) }
    } else if (worklogs.isEmpty()) {
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
                        Th({ style { textAlign("left"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Datum") }
                        Th({ style { textAlign("left"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Uren") }
                        Th({ style { textAlign("left"); padding(16.px); color(Styles.TextSecondary); fontWeight("600") } }) { Text("Beschrijving") }
                    }
                }
                Tbody {
                    worklogs.forEach { log ->
                        Tr({
                            style {
                                property("border-bottom", "1px solid ${Styles.Border}")
                            }
                        }) {
                            Td({ style { padding(16.px); color(Styles.TextPrimary) } }) { Text(log.createdAt) }
                            Td({ style { padding(16.px); color(Styles.TextPrimary) } }) {
                                Span({
                                    style {
                                        fontWeight("600")
                                    }
                                }) { Text(log.hours.toString()) }
                            }
                            Td({ style { padding(16.px); color(Styles.TextSecondary) } }) { Text(log.description ?: "") }
                        }
                    }
                }
            }
        }
    }
}
