package worklogtracker.webapp.ui

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.value
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLVideoElement
import worklogtracker.shared.dto.task.TaskResponse
import worklogtracker.shared.dto.worklog.CreateWorkLogRequest
import worklogtracker.webapp.ApiClient
import kotlin.js.Date
import kotlinx.browser.window


@Composable
fun WorkLogRegistrationScreen(api: ApiClient, scope: kotlinx.coroutines.CoroutineScope) {
    var tasks by remember { mutableStateOf<List<TaskResponse>>(emptyList()) }
    var selectedTaskId by remember { mutableStateOf<Int?>(null) }
    var notes by remember { mutableStateOf("") }
    var photoBase64 by remember { mutableStateOf<String?>(null) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var loading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("") }
    var showCamera by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            tasks = api.getTasks()
        } catch (e: Exception) {
            statusMessage = "Fout bij ophalen taken: ${e.message}"
        }
    }

    H2({ style { color(Styles.TextPrimary); marginBottom(24.px) } }) { Text("Uren Registreren") }

    Div({
        style {
            backgroundColor(Styles.Surface)
            padding(24.px)
            borderRadius(12.px)
            property("box-shadow", "0 1px 3px rgba(0,0,0,0.1)")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            gap(16.px)
        }
    }) {
        // Taak Selectie
        Div {
            Label(forId = "task-select") { Text("Selecteer Taak") }
            Select({
                id("task-select")
                onInput { event ->
                    selectedTaskId = event.value?.toIntOrNull()
                }
                style {
                    width(100.percent)
                    padding(8.px)
                    borderRadius(6.px)
                    border(1.px, LineStyle.Solid, Styles.Border)
                }
            }) {
                Option("", { value("") }) { Text("Selecteer een taak") }
                tasks.forEach { task ->
                    Option(task.id.toString(), { value(task.id.toString()) }) { Text(task.title) }
                }
            }
        }

        // Notities
        Div {
            Label(forId = "notes") { Text("Notities") }
//            TextArea({
//                id("notes")
//                value(notes)
//                onInput { event -> notes = event.value }
//                placeholder("Wat heb je gedaan?")
//                style {
//                    width(100.percent)
//                    padding(8.px)
//                    borderRadius(6.px)
//                    border(1.px, LineStyle.Solid, Styles.Border)
//                    minHeight(80.px)
//                }
//            }.toString())
        }

        // GPS Locatie
        Div {
            Button({
                onClick {
                    val geo = window.navigator.asDynamic().geolocation

                    geo.getCurrentPosition(
                        success = { pos: dynamic ->
                            val coords = pos.coords

                            latitude = coords.latitude
                            longitude = coords.longitude
                            statusMessage = "Locatie opgehaald!"
                        },
                        error = { err: dynamic ->
                            statusMessage = "Locatie fout: ${err.message}"
                        }
                    )
                }
                style {
                    padding(8.px, 16.px)
                    backgroundColor(Styles.Secondary)
                    color(Color.white)
                    border(0.px)
                    borderRadius(6.px)
                    cursor("pointer")
                }
            }) { Text(if (latitude != null) "Locatie OK ✓" else "Haal GPS Locatie op") }
            if (latitude != null) {
                P({ style { fontSize(0.8.em); color(Styles.TextSecondary) } }) {
                    Text("Lat: $latitude, Lon: $longitude")
                }
            }
        }

        // Camera
        Div {
            if (photoBase64 != null) {
                Img(src = photoBase64!!, alt = "Gemaakte foto") {
                    style { maxWidth(100.percent); borderRadius(8.px); marginBottom(8.px) }
                }
                Button({
                    onClick { photoBase64 = null }
                    style {
                        padding(8.px, 16.px)
                        backgroundColor(Styles.Error)
                        color(Color.white)
                        border(0.px)
                        borderRadius(6.px)
                        cursor("pointer")
                    }
                }) { Text("Opnieuw") }
            } else if (showCamera) {
                CameraComponent { base64 ->
                    photoBase64 = base64
                    showCamera = false
                }
            } else {
                Button({
                    onClick { showCamera = true }
                    style {
                        padding(8.px, 16.px)
                        backgroundColor(Styles.Primary)
                        color(Color.white)
                        border(0.px)
                        borderRadius(6.px)
                        cursor("pointer")
                    }
                }) { Text("Foto maken met Camera") }
            }
        }

        // Submit
        Button({
            if (loading || selectedTaskId == null) {
                disabled()
            }
            onClick {
                scope.launch {
                    loading = true
                    try {
                        val now = Date()
                        val startTime = now.toISOString()
                        val endTime = now.toISOString() // Simpel voor nu: start=eind

                        api.createWorkLog(
                            CreateWorkLogRequest(
                                taskId = selectedTaskId!!,
                                startTime = startTime,
                                endTime = endTime,
                                notes = notes,
                                photoBase64 = photoBase64,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                        statusMessage = "Uren succesvol geregistreerd!"
                        notes = ""
                        photoBase64 = null
                        latitude = null
                        longitude = null
                    } catch (e: Exception) {
                        statusMessage = "Fout bij opslaan: ${e.message}"
                    } finally {
                        loading = false
                    }
                }
            }
            style {
                padding(12.px)
                backgroundColor(if (selectedTaskId != null) Styles.Success else Styles.Secondary)
                color(Color.white)
                border(0.px)
                borderRadius(6.px)
                cursor("pointer")
                fontWeight("bold")
            }
        }) { Text(if (loading) "Opslaan..." else "Registreer Uren") }

        if (statusMessage.isNotEmpty()) {
            P({ style { color(if (statusMessage.contains("Fout")) Styles.Error else Styles.Success) } }) {
                Text(statusMessage)
            }
        }
    }
}

@Composable
fun CameraComponent(onPhotoCaptured: (String) -> Unit) {
    var videoElement: HTMLVideoElement? by remember { mutableStateOf(null) }

    Div {
        Video({
            id("video")
            ref { element ->
                videoElement = element
                window.navigator.mediaDevices.getUserMedia(
                    js("{video: true}")
                ).then({ stream ->
                    element.srcObject = stream
                    element.play()
                })
                onDispose {
                    (element.srcObject as? org.w3c.dom.mediacapture.MediaStream)?.getTracks()?.forEach { it.stop() }
                }
            }
            style { width(100.percent); borderRadius(8.px); backgroundColor(Color.black) }
        })

        Button({
            onClick {
                val canvas = document.createElement("canvas") as HTMLCanvasElement
                val context = canvas.getContext("2d") as CanvasRenderingContext2D
                videoElement?.let { video ->
                    canvas.width = video.videoWidth
                    canvas.height = video.videoHeight
                    context.drawImage(video, 0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
                    onPhotoCaptured(canvas.toDataURL("image/jpeg"))
                }
            }
            style {
                marginTop(8.px)
                padding(8.px, 16.px)
                backgroundColor(Styles.Primary)
                color(Color.white)
                border(0.px)
                borderRadius(6.px)
                cursor("pointer")
                width(100.percent)
            }
        }) { Text("Lach naar de camera! 📸") }
    }
}
