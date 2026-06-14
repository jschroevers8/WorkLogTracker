package worklogtracker.backend.application.usecases.auth

import worklogtracker.backend.application.exceptions.UserAuthenticationFailedException
import worklogtracker.backend.application.mappers.toResponse
import worklogtracker.backend.domain.auth.TokenGeneratorInterface
import worklogtracker.backend.domain.exceptions.UserNotFoundException
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.Email
import worklogtracker.shared.dto.auth.AuthResponse

class LoginUserUseCase(
    private val userRepository: UserRepositoryInterface,
    private val tokenGenerator: TokenGeneratorInterface,
) {
    suspend operator fun invoke(
        email: Email,
        plainPassword: String,
    ): AuthResponse {
        val user =
            userRepository.findByEmail(email)
                ?: throw UserNotFoundException(email.value)

        if (!user.verifyPassword(plainPassword)) {
            throw UserAuthenticationFailedException("Invalid credentials")
        }

        val token = tokenGenerator.generate(user.id!!)

        return user.toResponse(token)
    }
}
