package worklogtracker.presentation.framework.camera

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject

@Composable
fun rememberCameraController(
    cameraService: CameraService = koinInject(),
    onImageCaptured: (Uri) -> Unit
): () -> Unit {

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        cameraService.handleResult(success, onImageCaptured)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = cameraService.prepareCapture()
            cameraLauncher.launch(uri)
        }
    }

    return {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}
