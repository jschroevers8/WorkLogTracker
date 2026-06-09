package worklogtracker.presentation.framework.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.theme.RmcColors

@Composable
fun RmcSecondaryText(
    text: String,
    fontSize: Int = 13
) {
    Text(
        text = text,
        style = TextStyle(
            color = RmcColors.TextSecondary,
            fontSize = fontSize.sp,
        )
    )
}
