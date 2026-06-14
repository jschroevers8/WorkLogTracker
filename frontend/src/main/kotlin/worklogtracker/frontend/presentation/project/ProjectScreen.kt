package worklogtracker.frontend.presentation.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import worklogtracker.frontend.presentation.framework.BottomNavigationBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: ProjectViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadProjects()
    }

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
                text = "Projects",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.loading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            } else {
                val groupedProjects = uiState.projects.groupBy { it.status }
                LazyColumn {
                    groupedProjects.forEach { (status, projects) ->
                        stickyHeader {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Text(
                                    text = status,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                        items(projects) { project ->
                            ProjectCard(project)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: ProjectItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = project.name, style = MaterialTheme.typography.titleLarge)
            Text(text = project.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${project.status}", style = MaterialTheme.typography.labelSmall)
        }
    }
}
