package worklogtracker.presentation.framework.components.input

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePickerField(
    text: String,
    placeholder: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    calendar: Calendar = Calendar.getInstance(),
    dateFormat: SimpleDateFormat
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color(0xFF2C2C2C), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        TextButton(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        calendar.set(year, month, day)
                        onDateSelected(dateFormat.format(calendar.time))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text.ifEmpty { placeholder },
                color = if (text.isNotEmpty()) Color.White else Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}