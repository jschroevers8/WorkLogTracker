package worklogtracker.presentation.user.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import worklogtracker.presentation.framework.components.text.RmcPrimaryText
import worklogtracker.presentation.framework.components.text.RmcSecondaryText

@Composable
fun NotificationCard(notification: NotificationUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2C2C2C), RoundedCornerShape(14.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            RmcPrimaryText(notification.title)
            Spacer(Modifier.height(4.dp))
            RmcSecondaryText(notification.message)
        }

        RmcSecondaryText(notification.time, 12)
    }
}
