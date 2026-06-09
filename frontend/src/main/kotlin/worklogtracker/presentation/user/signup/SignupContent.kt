package worklogtracker.presentation.user.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import worklogtracker.presentation.framework.components.WltScreen
import worklogtracker.presentation.framework.components.button.WltPrimaryButton
import worklogtracker.presentation.framework.components.input.WltTextField
import worklogtracker.presentation.framework.components.text.WltClickableText
import worklogtracker.presentation.framework.theme.WltColors
import worklogtracker.presentation.user.components.WltLogoHeader
import worklogtracker.presentation.user.components.TermsText

@Composable
fun SignupContent(
    state: SignupUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignupClick: () -> Unit,
    onLoginClick: () -> Unit,
    backStack: NavBackStack<NavKey>,
) {
    WltScreen(backStack = backStack) {
        WltLogoHeader()

        WltTextField(
            state.firstName,
            "First Name",
            onFirstNameChange,
            modifier = Modifier.testTag("firstNameField")
        )
        Spacer(Modifier.height(16.dp))

        WltTextField(
            state.lastName,
            "Last Name",
            onLastNameChange,
            modifier = Modifier.testTag("lastNameField")
        )
        Spacer(Modifier.height(16.dp))

        WltTextField(
            state.email,
            "Email",
            onEmailChange,
            modifier = Modifier.testTag("emailField")
        )
        Spacer(Modifier.height(16.dp))

        WltTextField(
            state.phoneNumber,
            "Phone Number",
            onPhoneNumberChange,
            modifier = Modifier.testTag("phoneNumberField")
        )
        Spacer(Modifier.height(16.dp))

        WltTextField(
            state.password,
            "Password",
            onPasswordChange,
            modifier = Modifier.testTag("passwordField"),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(28.dp))

        WltPrimaryButton(
            text = "Sign Up",
            loading = state.loading,
            onClick = onSignupClick,
            modifier = Modifier.testTag("signupButton")
        )

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = WltColors.Error, modifier = Modifier.testTag("errorMessage"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        TermsText()

        Spacer(modifier = Modifier.height(16.dp))

        val annotatedSignIn = buildAnnotatedString {
            append("Already have an account? ")
            withStyle(SpanStyle(color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)) {
                append("Login now!")
            }
            addStringAnnotation("LOGIN", "login", length - "Login now!".length, length)
        }

        WltClickableText(
            text = annotatedSignIn,
            modifier = Modifier.fillMaxWidth().testTag("loginText"),
            onClick = { _ ->
                onLoginClick()
            }
        )
    }
}
