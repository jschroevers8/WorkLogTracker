package worklogtracker.webapp.ui.screens

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.testutils.runTest
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.viewmodel.ProjectsViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ProjectsUiTest {

    @Test
    fun testProjectsScreenStructure() = runTest {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))
        
        composition {
            ProjectsContent(
                loading = false,
                error = "",
                showCreateProject = false,
                newProjectName = "",
                newProjectDesc = "",
                activeProjects = emptyList(),
                onToggleCreateProject = {},
                onCreateProject = {},
                onUpdateNewProjectName = {},
                onUpdateNewProjectDesc = {},
                onClearError = {},
                onSeeProjectDetails = {},
                viewModel = viewModel,
                scope = this
            )
        }

        val title = root.querySelector("h2")
        assertEquals("Projecten & Beheer", title?.textContent)

        val toggleBtn = root.querySelector("#toggle-create-project") as HTMLButtonElement
        assertEquals("+ Nieuw Project", toggleBtn.textContent)

        val createForm = root.querySelector("#create-project-form")
        assertNull(createForm)
    }

    @Test
    fun testCreateProjectFormVisibility() = runTest {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))
        
        composition {
            ProjectsContent(
                loading = false,
                error = "",
                showCreateProject = true,
                newProjectName = "Test Project",
                newProjectDesc = "Test Desc",
                activeProjects = emptyList(),
                onToggleCreateProject = {},
                onCreateProject = {},
                onUpdateNewProjectName = {},
                onUpdateNewProjectDesc = {},
                onClearError = {},
                onSeeProjectDetails = {},
                viewModel = viewModel,
                scope = this
            )
        }

        val createForm = root.querySelector("#create-project-form") as HTMLDivElement
        assertNotNull(createForm)

        val nameInput = root.querySelector("#new-project-name") as HTMLInputElement
        assertEquals("Test Project", nameInput.value)

        val descInput = root.querySelector("#new-project-desc") as HTMLTextAreaElement
        assertEquals("Test Desc", descInput.value)

        val saveBtn = root.querySelector("#save-project") as HTMLButtonElement
        assertEquals("Project Opslaan", saveBtn.textContent)
    }
}
