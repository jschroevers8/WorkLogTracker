package worklogtracker.frontend.presentation.user.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import worklogtracker.frontend.presentation.framework.theme.Colors

@Composable
fun LogoHeader(spacingBottom: Int = 40) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(Modifier.height(30.dp))

        Icon(
            imageVector = Icons.Default.Work,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Colors.Primary,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "WorkLogTracker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.Primary,
        )

        Spacer(Modifier.height(spacingBottom.dp))
    }
}
