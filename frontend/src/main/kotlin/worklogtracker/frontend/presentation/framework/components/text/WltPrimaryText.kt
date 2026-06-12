package worklogtracker.frontend.presentation.framework.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import worklogtracker.frontend.presentation.framework.theme.WltColors

@Composable
fun WltPrimaryText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    fontWeight: FontWeight? = null,
    fontSize: Int = 15,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = WltColors.TextPrimary,
            fontSize = fontSize.sp,
            lineHeight = 16.sp,
            textAlign = textAlign,
            fontWeight = fontWeight,
        )
    )
}
