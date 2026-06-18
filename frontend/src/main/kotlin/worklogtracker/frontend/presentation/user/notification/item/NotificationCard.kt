package worklogtracker.frontend.presentation.user.notification.item

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
import worklogtracker.frontend.presentation.framework.components.text.PrimaryText
import worklogtracker.frontend.presentation.framework.components.text.SecondaryText
import worklogtracker.frontend.presentation.framework.theme.Colors
import worklogtracker.frontend.presentation.user.notification.item.NotificationItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

@Composable
fun NotificationCard(
    notification: NotificationItem,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    if (notification.isRead) Colors.Surface else Color(0xFFEFF6FF),
                    RoundedCornerShape(12.dp),
                ).clickable { if (!notification.isRead) onClick() }
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!notification.isRead) {
            Box(
                modifier =
                    Modifier
                        .width(8.dp)
                        .height(8.dp)
                        .background(Colors.Primary, RoundedCornerShape(4.dp)),
            )
            Spacer(Modifier.width(8.dp))
        } else {
            Spacer(Modifier.width(16.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            PrimaryText(notification.title)
            Spacer(Modifier.height(4.dp))
            SecondaryText(notification.message)
        }

        val formatter =
            DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .appendFraction(
                    ChronoField.NANO_OF_SECOND,
                    1,
                    9,
                    true,
                ).toFormatter()

        val dateTime = LocalDateTime.parse(notification.time, formatter)

        SecondaryText(
            dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            12,
        )
    }
}
