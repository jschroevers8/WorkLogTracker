package worklogtracker.application.usecases.auth

import worklogtracker.application.dto.auth.AuthResponse
import worklogtracker.domain.auth.TokenGeneratorInterface
import worklogtracker.application.exceptions.UserAuthenticationFailedException
import worklogtracker.application.mappers.toResponse
import worklogtracker.domain.exceptions.UserNotFoundException
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.user.Email

class LoginUserUseCase(
    private val userRepository: UserRepositoryInterface,
    private val tokenGenerator: TokenGeneratorInterface
) {
    
    suspend operator fun invoke(email: Email, plainPassword: String): AuthResponse {
        val user = userRepository.findByEmail(email) 
            ?: throw UserNotFoundException(email.value)
        
        if (!user.verifyPassword(plainPassword)) {
            throw UserAuthenticationFailedException("Invalid credentials")
        }

        val token = tokenGenerator.generate(user.id!!)

        return user.toResponse(token)
    }
}
