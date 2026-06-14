package worklogtracker.frontend.presentation.user.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import worklogtracker.frontend.presentation.framework.components.text.WltPrimaryText
import worklogtracker.frontend.presentation.framework.components.text.WltSecondaryText
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import worklogtracker.frontend.presentation.framework.theme.WltColors
import java.time.LocalDateTime

@Composable
fun NotificationCard(
    notification: NotificationItemUiState,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (notification.isRead) WltColors.Surface else Color(0xFFEFF6FF),
                RoundedCornerShape(12.dp)
            )
            .clickable { if (!notification.isRead) onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!notification.isRead) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .background(WltColors.Primary, RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.width(8.dp))
        } else {
            Spacer(Modifier.width(16.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            WltPrimaryText(notification.title)
            Spacer(Modifier.height(4.dp))
            WltSecondaryText(notification.message)
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

        val dateTime = LocalDateTime.parse(notification.time, formatter)

        WltSecondaryText(
            dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            12
        )    }
}
