package worklogtracker.presentation.advertisement.overview.show.map

import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.Position
import worklogtracker.data.remote.address.AddressResponse
import androidx.compose.ui.platform.LocalContext
import worklogtracker.data.remote.address.toSingleLine
import java.util.Locale

@Composable
fun MapScreen(modifier: Modifier = Modifier, address: AddressResponse) {
    val context = LocalContext.current

    val geocoder = Geocoder(context, Locale.getDefault())
    val results = geocoder.getFromLocationName(address.toSingleLine(), 1)

    val location = results!![0]

    val position = Position(
        latitude = location.latitude,
        longitude = location.longitude,
    )

    val cameraState = rememberCameraState(
        CameraPosition(
            target = position,
            zoom = 15.0,
            tilt = 20.0,
            bearing = 0.0
        )
    )

    Box(modifier = modifier) {
        MaplibreMap(
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
            cameraState = cameraState,
            styleState = rememberStyleState(),
            onMapClick = { _, _ -> ClickResult.Pass },
            options = MapOptions(
                ornamentOptions = OrnamentOptions.AllDisabled
            )
        ) {
            AddressLayer(position = position)
        }

        OverlayOrnaments(cameraState)
    }
}