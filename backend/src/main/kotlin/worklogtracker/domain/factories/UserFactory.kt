package worklogtracker.domain.factories

import worklogtracker.domain.entities.UserEntity
import worklogtracker.domain.entities.enums.UserRole
import worklogtracker.domain.valueobjects.user.Email
import worklogtracker.domain.valueobjects.user.Password
import java.time.LocalDateTime

class UserFactory {
    
    fun create(
        email: Email,
        plainPassword: String,
        firstName: String,
        lastName: String,
        role: UserRole = UserRole.EMPLOYEE
    ): UserEntity {
        return UserEntity(
            email = email,
            passwordHash = Password.Companion.create(plainPassword),
            firstName = firstName,
            lastName = lastName,
            role = role,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}

