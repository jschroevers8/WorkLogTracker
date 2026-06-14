package worklogtracker.frontend.presentation.framework.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import worklogtracker.frontend.presentation.framework.theme.Colors

@Composable
fun SecondaryText(
    text: String,
    fontSize: Int = 13,
) {
    Text(
        text = text,
        style =
            TextStyle(
                color = Colors.TextSecondary,
                fontSize = fontSize.sp,
            ),
    )
}
