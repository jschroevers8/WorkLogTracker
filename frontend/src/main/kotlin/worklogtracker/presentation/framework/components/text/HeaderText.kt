package worklogtracker.presentation.framework.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.theme.RmcColors

@Composable
fun HeaderText(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            color = RmcColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}