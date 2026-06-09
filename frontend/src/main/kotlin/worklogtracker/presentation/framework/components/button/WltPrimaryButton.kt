package worklogtracker.presentation.framework.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.theme.WltColors

@Composable
fun WltPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !loading,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = WltColors.Primary,
            disabledContainerColor = WltColors.Primary.copy(alpha = 0.4f)
        )
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = WltColors.TextPrimary,
                strokeWidth = 2.dp,
                modifier = Modifier.height(22.dp)
            )
        } else {
            Text(
                text = text,
                color = WltColors.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
