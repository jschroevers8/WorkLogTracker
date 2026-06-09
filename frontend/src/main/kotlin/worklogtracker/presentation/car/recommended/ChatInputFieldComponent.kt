package worklogtracker.presentation.car.recommended

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import worklogtracker.presentation.framework.components.input.RmcTextField
import worklogtracker.presentation.framework.theme.RmcColors

@Composable
fun ChatInputField(
    value: String,
    loading: Boolean,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        RmcTextField(
            placeholder = "Ask something about a car...",
            value = value,
            onValueChange = onValueChange
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp),
            enabled = value.isNotBlank() && !loading,
            onClick = onSend
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = if (value.isNotBlank())
                    RmcColors.Primary
                else
                    Color.Gray
            )
        }
    }
}