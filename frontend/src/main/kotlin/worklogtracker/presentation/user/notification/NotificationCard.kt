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
import worklogtracker.presentation.framework.components.text.WltPrimaryText
import worklogtracker.presentation.framework.components.text.WltSecondaryText

import worklogtracker.presentation.framework.theme.WltColors

@Composable
fun NotificationCard(notification: NotificationItemUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(WltColors.Surface, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            WltPrimaryText(notification.title)
            Spacer(Modifier.height(4.dp))
            WltSecondaryText(notification.message)
        }

        WltSecondaryText(notification.time, 12)
    }
}
