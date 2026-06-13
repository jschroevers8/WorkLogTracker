package worklogtracker.frontend.presentation.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.frontend.presentation.framework.BottomNavigationBar
import worklogtracker.frontend.navigation.Screen
import org.koin.compose.koinInject
import worklogtracker.frontend.presentation.worklog.WorkLogViewModel

@Composable
fun TaskScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: TaskViewModel = koinViewModel(),
    workLogViewModel: WorkLogViewModel = koinInject()
) {
    val uiState = viewModel.uiState

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                backStack = backStack,
                onItemSelected = { screen ->
                    backStack.add(screen)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Mijn Taken",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.loading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    items(uiState.tasks) { task ->
                        TaskCard(task) {
                            backStack.add(Screen.WorkLogs(task.id))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: TaskItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.titleLarge)
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Status: ${task.status}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
