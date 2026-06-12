package worklogtracker.frontend.presentation.framework.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String = "Bevestigen",
    dismissText: String = "Annuleren",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onConfirm, modifier = Modifier.testTag("DialogConfirm")) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.testTag("DialogDismiss")) {
                Text(dismissText)
            }
        }
    )
}
