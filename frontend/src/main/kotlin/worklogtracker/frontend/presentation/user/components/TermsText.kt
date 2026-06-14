package worklogtracker.frontend.presentation.user.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import worklogtracker.frontend.presentation.framework.theme.Colors

@Composable
fun TermsText() {
    Text(
        text =
            buildAnnotatedString {
                append("By clicking continue, you agree to our ")
                withStyle(SpanStyle(color = Color(0xFFD32F2F))) { append("Terms of Service") }
                append(" and ")
                withStyle(SpanStyle(color = Color(0xFFD32F2F))) { append("Privacy Policy") }
            },
        modifier = Modifier.fillMaxWidth(),
        style =
            TextStyle(
                color = Colors.TextPrimary,
                fontSize = 15.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
            ),
    )
}
