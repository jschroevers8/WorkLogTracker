package worklogtracker.backend.domain.valueobjects.user

import java.util.regex.Pattern

data class Email(
    val value: String,
) {
    init {
        require(isValid(value)) { "Invalid email format" }
    }

    companion object {
        private val EMAIL_REGEX =
            Pattern.compile(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
            )

        fun isValid(email: String): Boolean = EMAIL_REGEX.matcher(email).matches()
    }
}
