package worklogtracker.backend.application.usecases.auth

import worklogtracker.shared.dto.auth.AuthResponse
import worklogtracker.backend.application.exceptions.UserCreationFailedException
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.auth.TokenGeneratorInterface
import worklogtracker.backend.domain.factories.UserFactory
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.validations.user.ExistingUserValidator
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.backend.domain.entities.enums.UserRole

class RegisterUserUseCase(
    private val userRepository: UserRepositoryInterface,
    private val existingUserValidator: ExistingUserValidator,
    private val userFactory: UserFactory,
    private val tokenGenerator: TokenGeneratorInterface
) {
    
    suspend operator fun invoke(
        email: String,
        plainPassword: String,
        firstName: String,
        lastName: String,
        role: UserRole = UserRole.EMPLOYEE
    ): AuthResponse {
        return try {
            val emailVO = Email(email)
            
            existingUserValidator.validate(emailVO)
            
            val user = userFactory.create(
                email = emailVO,
                plainPassword = plainPassword,
                firstName = firstName,
                lastName = lastName,
                role = role
            )
            
            val savedUser = userRepository.save(user)
            val token = tokenGenerator.generate(user.id!!)

            savedUser.toResponse(token)
        } catch (e: Exception) {
            throw UserCreationFailedException(e.message ?: "Unknown error")
        }
    }
}
