package worklogtracker.presentation.advertisement.overview.show.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.GeoJsonSource
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position
import worklogtracker.presentation.framework.icons.Marker

@Composable
fun AddressLayer(position: Position) {
    val source: GeoJsonSource = rememberGeoJsonSource(
        data = GeoJsonData.Features(
            geoJson = FeatureCollection(
                features = listOf(
                    Feature(
                        geometry = Point(coordinates = position),
                        properties = buildJsonObject {
                            put("type", "Car")
                        }
                    )
                )
            )
        )
    )

    SymbolLayer(
        id = "address-symbol-layer",
        source = source,
        iconImage = image(
            value = rememberVectorPainter(image = Marker),
            drawAsSdf = true
        ),
        iconColor = const(Color.Red),
        iconSize = const(1.5f)
    )
}
