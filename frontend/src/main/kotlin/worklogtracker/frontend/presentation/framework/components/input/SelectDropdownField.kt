package worklogtracker.frontend.presentation.framework.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T> SelectDropdownField(
    value: T?,
    placeholder: String,
    options: List<T>,
    modifier: Modifier = Modifier,
    labelMapper: (T) -> String = { it.toString() },
    onSelect: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var fieldWidth by remember { mutableStateOf(0) }

    Box(modifier = modifier) {
        TextField(
            value = value?.let(labelMapper) ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(placeholder, color = Color.Gray)
            },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .onGloballyPositioned { fieldWidth = it.size.width },
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2C2C),
                unfocusedContainerColor = Color(0xFF2C2C2C),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { fieldWidth.toDp() })
                .background(Color(0xFF2C2C2C))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(labelMapper(option), color = Color.White)
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
