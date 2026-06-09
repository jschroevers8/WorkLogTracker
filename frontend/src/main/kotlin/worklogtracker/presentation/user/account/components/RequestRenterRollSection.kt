package worklogtracker.presentation.user.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import worklogtracker.presentation.framework.components.ConfirmDialog
import worklogtracker.presentation.user.account.AccountUiState

@Composable
fun RequestRenterRollSection(
    state: AccountUiState,
    showDialog: Boolean,
    onShowDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onConfirm: () -> Unit
) {
    if (state.renterRightsRequested) {
        return
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(Color(0xFF2C2C2C), RoundedCornerShape(14.dp))
            .clickable { onShowDialog() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.RecordVoiceOver,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(Modifier.width(16.dp))
        Text("Request Renter Roll", color = Color.White, fontSize = 15.sp)
    }

    if (showDialog) {
        ConfirmDialog(
            title = "Renter Roll aanvragen",
            message = "Weet je zeker dat je deze roll wilt aanvragen?",
            confirmText = "Ja",
            dismissText = "Nee",
            onConfirm = onConfirm,
            onDismiss = onDismissDialog
        )
    }
}
