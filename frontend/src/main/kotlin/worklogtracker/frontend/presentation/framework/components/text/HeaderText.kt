package worklogtracker.frontend.presentation.framework.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import worklogtracker.frontend.presentation.framework.theme.Colors

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style =
            TextStyle(
                color = Colors.TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
    )
}
