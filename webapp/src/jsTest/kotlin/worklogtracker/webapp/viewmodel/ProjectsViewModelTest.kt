package worklogtracker.webapp.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import worklogtracker.webapp.ApiClient
import kotlin.test.*

// Simple manual mock for ApiClient and repositories since MockK is not easily available in JS tests here
class MockApiClient : ApiClient() {
    // In a real scenario, we would mock the individual repositories or the HttpClient
    // For this example, we'll keep it simple or try to use Ktor MockEngine if needed.
}

class ProjectsViewModelTest {

    // Note: JS tests have some limitations with coroutines and mocking.
    // Given the environment, I'll provide a structure for the test.

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
        viewModel.toggleCreateProject()
        assertTrue(viewModel.showCreateProject)
    }

    @Test
    fun testClearError() {
        val viewModel = ProjectsViewModel(ApiClient(), CoroutineScope(Dispatchers.Default))
        viewModel.error = "Some error"
        viewModel.clearError()
        assertEquals("", viewModel.error)
    }
}
