package worklogtracker.presentation.framework.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.theme.WltColors

@Composable
fun WltSecondaryText(
    text: String,
    fontSize: Int = 13
) {
    Text(
        text = text,
        style = TextStyle(
            color = WltColors.TextSecondary,
            fontSize = fontSize.sp,
        )
    )
}
