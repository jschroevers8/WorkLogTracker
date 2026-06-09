package worklogtracker.presentation.framework.components.input

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import worklogtracker.presentation.framework.theme.RmcColors

@Composable
fun RmcTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = RmcColors.Surface,
            unfocusedContainerColor = RmcColors.Surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = RmcColors.TextPrimary,
            focusedTextColor = RmcColors.TextPrimary,
            unfocusedTextColor = RmcColors.TextPrimary,
            focusedPlaceholderColor = RmcColors.TextSecondary,
            unfocusedPlaceholderColor = RmcColors.TextSecondary
        )
    )
}
