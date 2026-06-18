package worklogtracker.webapp.viewmodel

import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import worklogtracker.webapp.ApiClient
import kotlin.test.*

class ProjectsViewModelTest {
    @Test
    fun testInitialState() {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))
        assertTrue(viewModel.loading)
        assertEquals("", viewModel.error)
        assertTrue(viewModel.activeProjects.isEmpty())
    }

    @Test
    fun testToggleCreateProject() {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))
        assertFalse(viewModel.showCreateProject)

        Snapshot.withMutableSnapshot {
            viewModel.toggleCreateProject()
        }

        assertTrue(viewModel.showCreateProject)
    }

    @Test
    fun testClearError() {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))

        Snapshot.withMutableSnapshot {
            viewModel.error = "Some error"
        }
        assertEquals("Some error", viewModel.error)

        Snapshot.withMutableSnapshot {
            viewModel.clearError()
        }
        assertEquals("", viewModel.error)
    }

    @Test
    fun testUpdateProjectFields() {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))

        Snapshot.withMutableSnapshot {
            viewModel.newProjectName = "New Project"
            viewModel.newProjectDesc = "New Description"
        }

        assertEquals("New Project", viewModel.newProjectName)
        assertEquals("New Description", viewModel.newProjectDesc)
    }
}
