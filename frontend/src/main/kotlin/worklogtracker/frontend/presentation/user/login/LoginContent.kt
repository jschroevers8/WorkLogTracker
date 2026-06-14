package worklogtracker.frontend.presentation.user.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.frontend.presentation.framework.components.Screen
import worklogtracker.frontend.presentation.framework.components.button.PrimaryButton
import worklogtracker.frontend.presentation.framework.components.input.TextField
import worklogtracker.frontend.presentation.framework.components.text.ClickableText
import worklogtracker.frontend.presentation.framework.theme.Colors
import worklogtracker.frontend.presentation.user.components.LogoHeader
import worklogtracker.frontend.presentation.user.components.TermsText

@Composable
fun LoginContent(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit,
    backStack: NavBackStack<NavKey>,
) {
    Screen(backStack = backStack) {
        LogoHeader()

        TextField(
            value = state.email,
            placeholder = "Email",
            onValueChange = onEmailChange,
            modifier = Modifier.testTag("emailField"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.password,
            placeholder = "Password",
            onValueChange = onPasswordChange,
            modifier = Modifier.testTag("passwordField"),
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(28.dp))

        PrimaryButton(
            text = "Login",
            loading = state.loading,
            onClick = onLoginClick,
            modifier = Modifier.testTag("loginButton"),
        )

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = Colors.Error, modifier = Modifier.testTag("errorMessage"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        TermsText()

        Spacer(modifier = Modifier.height(16.dp))

        val annotatedSignUp =
            buildAnnotatedString {
                append("Don't have an account yet? ")
                withStyle(SpanStyle(color = Colors.Primary, fontWeight = FontWeight.Bold)) {
                    append("Sign up now!")
                }
                addStringAnnotation("SIGN_UP", "signup", length - "Sign up now!".length, length)
            }

        ClickableText(
            text = annotatedSignUp,
            modifier = Modifier.fillMaxWidth().testTag("signupText"),
            onClick = { _ ->
                onSignupClick()
            },
        )
    }
}
