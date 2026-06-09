package worklogtracker.presentation.user.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton
import worklogtracker.presentation.framework.components.input.RmcTextField
import worklogtracker.presentation.framework.components.text.RmcClickableText
import worklogtracker.presentation.framework.theme.RmcColors
import worklogtracker.presentation.user.components.RmcLogoHeader
import worklogtracker.presentation.user.components.TermsText

@Composable
fun LoginContent(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit,
    backStack: NavBackStack<NavKey>
) {
    RmcScreen(backStack = backStack) {
        RmcLogoHeader()

        RmcTextField(
            value = state.email,
            placeholder = "Email",
            onValueChange = onEmailChange,
            modifier = Modifier.testTag("emailField")
        )

        Spacer(modifier = Modifier.height(16.dp))

        RmcTextField(
            value = state.password,
            placeholder = "Password",
            onValueChange = onPasswordChange,
            modifier = Modifier.testTag("passwordField"),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(28.dp))

        RmcPrimaryButton(
            text = "Login",
            loading = state.loading,
            onClick = onLoginClick,
            modifier = Modifier.testTag("loginButton")
        )

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = RmcColors.Error, modifier = Modifier.testTag("errorMessage"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        TermsText()

        Spacer(modifier = Modifier.height(16.dp))

        val annotatedSignUp = buildAnnotatedString {
            append("Don't have an account yet? ")
            withStyle(SpanStyle(color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)) {
                append("Sign up now!")
            }
            addStringAnnotation("SIGN_UP", "signup", length - "Sign up now!".length, length)
        }

        RmcClickableText(
            text = annotatedSignUp,
            modifier = Modifier.fillMaxWidth().testTag("signupText"),
            onClick = { _ ->
                onSignupClick()
            }
        )
    }
}