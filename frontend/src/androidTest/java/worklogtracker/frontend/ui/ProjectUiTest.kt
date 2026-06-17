package worklogtracker.frontend.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import worklogtracker.frontend.presentation.project.ProjectContent
import worklogtracker.frontend.presentation.project.ProjectUiState
import worklogtracker.frontend.presentation.project.item.ProjectItem

class ProjectUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun projectScreen_showsLoading() {
        val mockBackStack = mockk<NavBackStack<NavKey>>(relaxed = true)
        
        composeTestRule.setContent {
            ProjectContent(
                uiState = ProjectUiState(loading = true),
                loadProjects = {},
                backStack = mockBackStack
            )
        }

        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }

    @Test
    fun projectScreen_showsError() {
        val mockBackStack = mockk<NavBackStack<NavKey>>(relaxed = true)
        val errorMessage = "Failed to load projects"
        
        composeTestRule.setContent {
            ProjectContent(
                uiState = ProjectUiState(loading = false, error = errorMessage),
                loadProjects = {},
                backStack = mockBackStack
            )
        }

        composeTestRule.onNodeWithTag("errorMessage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun projectScreen_showsProjectList() {
        val mockBackStack = mockk<NavBackStack<NavKey>>(relaxed = true)
        val projects = listOf(
            ProjectItem(1, "Project Alpha", "Description Alpha", "ACTIVE"),
            ProjectItem(2, "Project Beta", "Description Beta", "PLANNING")
        )
        
        composeTestRule.setContent {
            ProjectContent(
                uiState = ProjectUiState(loading = false, projects = projects),
                loadProjects = {},
                backStack = mockBackStack
            )
        }

        composeTestRule.onNodeWithTag("projectList").assertIsDisplayed()
        composeTestRule.onNodeWithText("Project Alpha").assertIsDisplayed()
        composeTestRule.onNodeWithText("Project Beta").assertIsDisplayed()
        composeTestRule.onNodeWithText("ACTIVE").assertIsDisplayed()
        composeTestRule.onNodeWithText("PLANNING").assertIsDisplayed()
    }
}
