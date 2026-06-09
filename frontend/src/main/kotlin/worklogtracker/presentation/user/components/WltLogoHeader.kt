package worklogtracker.presentation.user.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import worklogtracker.R

@Composable
fun WltLogoHeader(spacingBottom: Int = 40) {
    Spacer(Modifier.height(30.dp))

    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = null,
        modifier = Modifier.height(120.dp)
    )

    Spacer(Modifier.height(8.dp))

    Image(
        painter = painterResource(R.drawable.text),
        contentDescription = null,
        modifier = Modifier.height(40.dp)
    )

    Spacer(Modifier.height(spacingBottom.dp))
}
