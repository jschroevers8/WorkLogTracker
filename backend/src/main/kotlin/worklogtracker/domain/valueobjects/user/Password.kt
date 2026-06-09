package worklogtracker.domain.valueobjects.user

import at.favre.lib.crypto.bcrypt.BCrypt
import java.util.regex.Pattern

data class Password(val hash: String) {
    companion object {
        private val PASSWORD_REGEX = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
        )

        fun create(plainPassword: String): Password {
            println(plainPassword)
            require(isValid(plainPassword)) { 
                "Password must be at least 8 characters with uppercase, lowercase, digit and special character" 
            }
            val hash = BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray())
            return Password(hash)
        }

        fun fromHash(hash: String): Password {
            return Password(hash)
        }

        fun isValid(password: String): Boolean {
            return PASSWORD_REGEX.matcher(password).matches()
        }

        fun verify(plainPassword: String, hash: String): Boolean {
            return BCrypt.verifyer().verify(plainPassword.toCharArray(), hash).verified
        }
    }
}

