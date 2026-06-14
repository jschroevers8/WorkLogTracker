package worklogtracker.frontend.presentation.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.frontend.navigation.Screen
import worklogtracker.frontend.presentation.framework.BottomNavigationBar
import worklogtracker.frontend.presentation.task.item.TaskCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: TaskViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                backStack = backStack,
                onItemSelected = { screen ->
                    backStack.add(screen)
                },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            Text(
                text = "Mijn Taken",
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.loading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            } else {
                val groupedTasks = uiState.tasks.groupBy { it.status }
                LazyColumn {
                    groupedTasks.forEach { (status, tasks) ->
                        stickyHeader {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                            ) {
                                Text(
                                    text = status,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                )
                            }
                        }
                        items(tasks) { task ->
                            val isCompleted = task.status.equals("Completed", ignoreCase = true)
                            TaskCard(task, enabled = !isCompleted) {
                                if (!isCompleted) {
                                    backStack.add(Screen.WorkLogs(task.id))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
