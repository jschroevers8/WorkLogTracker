package worklogtracker.frontend.presentation.user.login

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import worklogtracker.frontend.data.auth.AuthManagerInterface
import worklogtracker.frontend.repositories.UserRepository
import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.shared.dto.auth.LoginRequest

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val userRepository = mockk<UserRepository>()
    private val authManager = mockk<AuthManagerInterface>(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(userRepository, authManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login should succeed with valid credentials`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "Password123!"
        val token = "valid_token"
        val authResponse = AuthResponse(
            token = token,
            firstName = "John",
            lastName = "Doe",
            email = email,
            role = "EMPLOYEE",
            userId = 1
        )

        viewModel.updateState<String> { copy(email = it) }(email)
        viewModel.updateState<String> { copy(password = it) }(password)

        coEvery { userRepository.login(any()) } returns authResponse

        var successCalled = false
        viewModel.onLoginSuccess = { successCalled = true }

        // Act
        viewModel.login()
        advanceUntilIdle()

        // Assert
        coVerify { userRepository.login(LoginRequest(email, password)) }
        coVerify { authManager.saveAuthToken(token) }
        coVerify { authManager.saveUserData(any()) }
        assertTrue(successCalled)
        assertNull(viewModel.uiState.error)
    }

    @Test
    fun `login should show error with invalid email`() = runTest {
        // Arrange
        viewModel.updateState<String> { copy(email = "invalid-email") }("invalid-email")
        viewModel.updateState<String> { copy(password = "Password123!") }("Password123!")

        // Act
        viewModel.login()
        advanceUntilIdle()

        // Assert
        assertNotNull(viewModel.uiState.error)
        coVerify(exactly = 0) { userRepository.login(any()) }
    }

    @Test
    fun `login should show error when API fails`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "Password123!"
        viewModel.updateState<String> { copy(email = it) }(email)
        viewModel.updateState<String> { copy(password = it) }(password)

        coEvery { userRepository.login(any()) } throws Exception("Network Error")

        // Act
        viewModel.login()
        advanceUntilIdle()

        // Assert
        assertEquals("Network Error", viewModel.uiState.error)
    }
}
