package worklogtracker.backend.domain.valueobjects.user

import at.favre.lib.crypto.bcrypt.BCrypt
import java.util.regex.Pattern

data class Password(
    val hash: String,
) {
    companion object {
        private val PASSWORD_REGEX =
            Pattern.compile(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            )

        fun create(plainPassword: String): Password {
            require(isValid(plainPassword)) {
                "Password must be at least 8 characters with uppercase, lowercase, digit and special character"
            }
            val hash = BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray())
            return Password(hash)
        }

        fun fromHash(hash: String): Password = Password(hash)

        fun isValid(password: String): Boolean = PASSWORD_REGEX.matcher(password).matches()

        fun verify(
            plainPassword: String,
            hash: String,
        ): Boolean = BCrypt.verifyer().verify(plainPassword.toCharArray(), hash).verified
    }
}
