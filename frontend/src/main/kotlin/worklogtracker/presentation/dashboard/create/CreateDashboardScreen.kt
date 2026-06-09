package worklogtracker.presentation.dashboard.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.framework.components.text.RmcPrimaryText
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.button.RmcSecondaryButton

@Composable
fun CreateDashboardScreen(backStack: NavBackStack<NavKey>) {
    RmcScreen(backStack = backStack, verticalArrangement = Arrangement.Center) {
        RmcPrimaryText(
            "What do you want to create?",
            fontSize = 24,
        )

        Spacer(Modifier.height(40.dp))

        RmcSecondaryButton("Create a Car") { backStack.add(Screen.CreateCar) }

        Spacer(Modifier.height(16.dp))

        RmcSecondaryButton("Create a Advertisement") { backStack.add(Screen.Advertisement) }
    }
}
