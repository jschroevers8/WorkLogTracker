package worklogtracker.presentation.framework.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Marker: ImageVector
    get() {
        if (_IconName != null) {
            return _IconName!!
        }
        _IconName = ImageVector.Builder(
            name = "IconName",
            defaultWidth = 20.dp,
            defaultHeight = 56.dp,
            viewportWidth = 20f,
            viewportHeight = 56f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF262626)),
                fillAlpha = 0.2f,
                strokeAlpha = 0.2f
            ) {
                moveTo(1f, 27f)
                arcToRelative(9f, 5f, 0f, isMoreThanHalf = true, isPositiveArc = false, 18f, 0f)
                arcToRelative(9f, 5f, 0f, isMoreThanHalf = true, isPositiveArc = false, -18f, 0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFF84D4D)),
                stroke = SolidColor(Color(0xFF951212)),
                strokeLineWidth = 1.0229f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(19.5f, 10.4f)
                curveToRelative(0f, 6.3f, -9.5f, 17.1f, -9.5f, 17.1f)
                reflectiveCurveTo(0.5f, 16.6f, 0.5f, 10.4f)
                curveToRelative(0f, -5.5f, 4.3f, -9.9f, 9.5f, -9.9f)
                reflectiveCurveTo(19.5f, 4.9f, 19.5f, 10.4f)
                close()
            }
            path(
                fill = SolidColor(Color.White),
                stroke = SolidColor(Color(0xFF7C2525)),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(10f, 10f)
                moveToRelative(-3.8f, 0f)
                arcToRelative(3.8f, 3.8f, 0f, isMoreThanHalf = true, isPositiveArc = true, 7.6f, 0f)
                arcToRelative(3.8f, 3.8f, 0f, isMoreThanHalf = true, isPositiveArc = true, -7.6f, 0f)
            }
        }.build()

        return _IconName!!
    }

@Suppress("ObjectPropertyName")
private var _IconName: ImageVector? = null