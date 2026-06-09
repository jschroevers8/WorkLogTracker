package worklogtracker.presentation.user.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.theme.WltColors

@Composable
fun AccountRow(
    text: String,
    testText: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color = WltColors.Primary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(WltColors.Surface, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp)
            .testTag("AccountRow_$testText"),
    verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
        Spacer(Modifier.width(16.dp))
        Text(text, color = WltColors.TextPrimary, fontSize = 15.sp)
    }
}