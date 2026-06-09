package worklogtracker.presentation.advertisement.overview.show.map

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.maplibre.compose.camera.CameraState
import org.maplibre.compose.material3.DisappearingCompassButton
import org.maplibre.compose.material3.ScaleBar



@Composable
fun BoxScope.OverlayOrnaments(cameraState: CameraState) {
    ScaleBar(
        metersPerDp = cameraState.metersPerDpAtTarget,
        modifier = Modifier.align(Alignment.TopStart),
    )

    DisappearingCompassButton(
        cameraState = cameraState,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(16.dp),
    )
}