package worklogtracker.frontend.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import worklogtracker.frontend.presentation.user.login.LoginContent
import worklogtracker.frontend.presentation.user.login.LoginUiState

class NavigationUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_clickSignup_callsOnSignupClick() {
        val mockBackStack = mockk<NavBackStack<NavKey>>(relaxed = true)
        val onSignupClick = mockk<() -> Unit>(relaxed = true)
        
        composeTestRule.setContent {
            LoginContent(
                state = LoginUiState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                onSignupClick = onSignupClick,
                backStack = mockBackStack
            )
        }

        // Act
        composeTestRule.onNodeWithTag("signupText").performClick()

        // Assert
        verify { onSignupClick() }
    }
}
