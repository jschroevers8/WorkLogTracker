package worklogtracker.frontend.presentation.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.frontend.presentation.framework.BottomNavigationBar
import worklogtracker.frontend.presentation.project.item.ProjectCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: ProjectViewModel = koinViewModel(),
) {
    ProjectContent(
        uiState = viewModel.uiState,
        loadProjects = { viewModel.loadProjects() },
        backStack = backStack,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectContent(
    uiState: ProjectUiState,
    loadProjects: () -> Unit,
    backStack: NavBackStack<NavKey>,
) {
    LaunchedEffect(Unit) {
        loadProjects()
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
                text = "Projects",
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.loading) {
                CircularProgressIndicator(modifier = Modifier.testTag("loadingIndicator"))
            } else if (uiState.error != null) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("errorMessage"),
                )
            } else {
                val groupedProjects = uiState.projects.groupBy { it.status }
                LazyColumn(modifier = Modifier.testTag("projectList")) {
                    groupedProjects.forEach { (status, projects) ->
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
                        items(projects) { project ->
                            ProjectCard(project)
                        }
                    }
                }
            }
        }
    }
}
