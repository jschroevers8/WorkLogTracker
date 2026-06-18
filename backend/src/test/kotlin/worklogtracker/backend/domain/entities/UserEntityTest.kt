package worklogtracker.backend.domain.entities

import org.junit.Test
import worklogtracker.backend.domain.entities.enums.UserRole
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.backend.domain.valueobjects.user.Password
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class UserEntityTest {
    private val validEmail = Email("test@example.com")
    private val passwordHash = Password.fromHash("\$2a\$12\$6vL/XFzG0K/kR.w9D7uI7.E6U5J2n9L3hG6o8/mI8z0G9J0K1L2M3") // Fake hash for testing
    private val now = LocalDateTime.now()

    @Test
    fun `ensureIsAdmin should not throw exception for ADMIN role`() {
        val user =
            UserEntity(
                id = UserId(1),
                email = validEmail,
                passwordHash = passwordHash,
                firstName = "Admin",
                lastName = "User",
                role = UserRole.ADMIN,
                createdAt = now,
                updatedAt = now,
            )

        user.ensureIsAdmin() // Should not throw
    }

    @Test
    fun `ensureIsAdmin should throw UnauthorizedException for EMPLOYEE role`() {
        val user =
            UserEntity(
                id = UserId(1),
                email = validEmail,
                passwordHash = passwordHash,
                firstName = "Employee",
                lastName = "User",
                role = UserRole.EMPLOYEE,
                createdAt = now,
                updatedAt = now,
            )

        assertFailsWith<UnauthorizedException> {
            user.ensureIsAdmin()
        }
    }

    @Test
    fun `ensureIsTeamMember should not throw exception for EMPLOYEE role`() {
        val user =
            UserEntity(
                id = UserId(1),
                email = validEmail,
                passwordHash = passwordHash,
                firstName = "Employee",
                lastName = "User",
                role = UserRole.EMPLOYEE,
                createdAt = now,
                updatedAt = now,
            )

        user.ensureIsTeamMember() // Should not throw
    }

    @Test
    fun `ensureIsTeamMember should throw UnauthorizedException for ADMIN role`() {
        val user =
            UserEntity(
                id = UserId(1),
                email = validEmail,
                passwordHash = passwordHash,
                firstName = "Admin",
                lastName = "User",
                role = UserRole.ADMIN,
                createdAt = now,
                updatedAt = now,
            )

        assertFailsWith<UnauthorizedException> {
            user.ensureIsTeamMember()
        }
    }
}
