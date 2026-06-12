package worklogtracker.backend.domain.entities

import worklogtracker.backend.domain.entities.enums.UserRole
import worklogtracker.backend.domain.exceptions.UnauthorizedException
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.backend.domain.valueobjects.user.Password
import worklogtracker.backend.domain.valueobjects.user.UserId
import java.time.LocalDateTime

/**
 * User Aggregate Root
 * 
 * Principal actor in the system. Manages authentication and role-based access control.
 * Domain Rules:
 * - Email must be unique
 * - Password must meet security requirements
 * - Only admins can create other users
 */
data class UserEntity(
    val id: UserId? = null,
    val email: Email,
    val passwordHash: Password,
    val firstName: String,
    val lastName: String,
    val role: UserRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    
    /**
     * Authorization check: Only admins can perform admin operations
     */
    fun ensureIsAdmin() {
        if (role != UserRole.ADMIN) {
            throw UnauthorizedException("User must have ADMIN role")
        }
    }

    /**
     * Authorization check: User must be employee or team leader
     */
    fun ensureIsTeamMember() {
        if (role == UserRole.ADMIN) {
            throw UnauthorizedException("Admin cannot be assigned to tasks")
        }
    }

    /**
     * Verify password matches stored hash
     */
    fun verifyPassword(plainPassword: String): Boolean {
        return Password.Companion.verify(plainPassword, passwordHash.hash)
    }
}

