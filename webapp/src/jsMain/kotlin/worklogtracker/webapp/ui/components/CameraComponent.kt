package worklogtracker.webapp.ui.components

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLVideoElement
import worklogtracker.webapp.ui.Styles

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
