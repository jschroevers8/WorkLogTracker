package worklogtracker.frontend.presentation.user.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    Screen(backStack = backStack) {
        LogoHeader()

        TextField(
            state.firstName,
            "First Name",
            onFirstNameChange,
            modifier = Modifier.testTag("firstNameField"),
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            state.lastName,
            "Last Name",
            onLastNameChange,
            modifier = Modifier.testTag("lastNameField"),
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            state.email,
            "Email",
            onEmailChange,
            modifier = Modifier.testTag("emailField"),
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            state.phoneNumber,
            "Phone Number",
            onPhoneNumberChange,
            modifier = Modifier.testTag("phoneNumberField"),
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            state.password,
            "Password",
            onPasswordChange,
            modifier = Modifier.testTag("passwordField"),
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(Modifier.height(28.dp))

        PrimaryButton(
            text = "Sign Up",
            loading = state.loading,
            onClick = onSignupClick,
            modifier = Modifier.testTag("signupButton"),
        )

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = Colors.Error, modifier = Modifier.testTag("errorMessage"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        TermsText()

        Spacer(modifier = Modifier.height(16.dp))

        val annotatedSignIn =
            buildAnnotatedString {
                append("Already have an account? ")
                withStyle(SpanStyle(color = Colors.Primary, fontWeight = FontWeight.Bold)) {
                    append("Login now!")
                }
                addStringAnnotation("LOGIN", "login", length - "Login now!".length, length)
            }

        ClickableText(
            text = annotatedSignIn,
            modifier = Modifier.fillMaxWidth().testTag("loginText"),
            onClick = { _ ->
                onLoginClick()
            },
        )
    }
}
