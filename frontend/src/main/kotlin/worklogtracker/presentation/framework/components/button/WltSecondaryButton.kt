package worklogtracker.presentation.framework.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.theme.WltColors

@Composable
fun WltSecondaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = WltColors.Surface)
    ) {
        Text(
            text = text,
            color = WltColors.TextPrimary,
            fontSize = 18.sp,
        )
    }
}
