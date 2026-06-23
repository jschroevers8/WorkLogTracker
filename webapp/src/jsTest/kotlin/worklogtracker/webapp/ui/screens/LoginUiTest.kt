package worklogtracker.webapp.ui.screens

import org.jetbrains.compose.web.testutils.runTest
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUiTest {

    @Test
    fun testLoginScreenStructure() = runTest {
        composition {
            LoginContent(
                email = "",
                password = "",
                error = "",
                loading = false,
                onEmailInput = {},
                onPasswordInput = {},
                onLoginClick = {}
            )
        }

        val emailInput = root.querySelector("#email") as HTMLInputElement
        val passwordInput = root.querySelector("#password") as HTMLInputElement
        val loginButton = root.querySelector("button") as HTMLButtonElement

        assertEquals("E-mailadres", root.querySelector("label[for='email']")?.textContent)
        assertEquals("Wachtwoord", root.querySelector("label[for='password']")?.textContent)
        assertEquals("Inloggen", loginButton.textContent)
    }

    @Test
    fun testLoginButtonDisabledWhenLoading() = runTest {
        composition {
            LoginContent(
                email = "",
                password = "",
                error = "",
                loading = true,
                onEmailInput = {},
                onPasswordInput = {},
                onLoginClick = {}
            )
        }

        val loginButton = root.querySelector("button") as HTMLButtonElement
        assertEquals("Bezig met inloggen...", loginButton.textContent)
        assertEquals(true, loginButton.disabled)
    }
}
