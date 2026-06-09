package worklogtracker.presentation.framework.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraService(
    private val context: Context
) {
    private var cameraUri: Uri? = null

    fun createImageUri(): Uri {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val file = File(context.cacheDir, "JPEG_$timestamp.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun prepareCapture(): Uri {
        cameraUri = createImageUri()
        return cameraUri!!
    }

    fun handleResult(
        success: Boolean,
        onSuccess: (Uri) -> Unit
    ) {
        if (success) {
            cameraUri?.let(onSuccess)
        }
    }
}
