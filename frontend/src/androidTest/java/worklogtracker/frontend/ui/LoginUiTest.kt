package worklogtracker.frontend.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertIsDisplayed
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import worklogtracker.frontend.presentation.user.login.LoginContent
import worklogtracker.frontend.presentation.user.login.LoginUiState

class LoginUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_showsEmailAndPasswordFields() {
        val mockBackStack = mockk<NavBackStack<NavKey>>(relaxed = true)
        
        composeTestRule.setContent {
            LoginContent(
                state = LoginUiState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                onSignupClick = {},
                backStack = mockBackStack
            )
        }

        composeTestRule.onNodeWithTag("emailField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("passwordField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginButton").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsErrorMessage() {
        val mockBackStack = mockk<NavBackStack<NavKey>>(relaxed = true)
        val errorMessage = "Invalid credentials"
        
        composeTestRule.setContent {
            LoginContent(
                state = LoginUiState(error = errorMessage),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                onSignupClick = {},
                backStack = mockBackStack
            )
        }

        composeTestRule.onNodeWithTag("errorMessage").assertIsDisplayed()
    }
}
