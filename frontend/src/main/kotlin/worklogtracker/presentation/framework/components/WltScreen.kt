package worklogtracker.presentation.framework.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.presentation.framework.theme.WltColors
import worklogtracker.presentation.framework.BottomNavigationBar

@Composable
fun WltScreen(
    modifier: Modifier = Modifier,
    backStack: NavBackStack<NavKey>,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(backStack = backStack) { screen ->
                backStack.add(screen)
            }
        },
        containerColor = WltColors.Background
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = verticalArrangement,
            content = content

        )
    }
}

