package worklogtracker.frontend.presentation.framework.components.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import worklogtracker.frontend.presentation.framework.theme.Colors

@Composable
fun ClickableText(
    text: AnnotatedString,
    onClick: (Int) -> Unit,
    modifier: Modifier,
) {
    ClickableText(
        text = text,
        style =
            TextStyle(
                color = Colors.TextPrimary,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
            ),
        onClick = onClick,
        modifier = modifier,
    )
}
