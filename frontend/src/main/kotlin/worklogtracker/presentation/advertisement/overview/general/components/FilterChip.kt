package worklogtracker.presentation.advertisement.overview.general.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterChip(label: String) {
    Box(
        Modifier
            .background(Color(0xFF2C2C2C), RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp, horizontal = 16.dp),
    ) {
        Text(label, color = Color.White)
    }
}
