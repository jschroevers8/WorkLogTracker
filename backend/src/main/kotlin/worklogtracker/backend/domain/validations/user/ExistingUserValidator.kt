package worklogtracker.backend.domain.validations.user

import worklogtracker.backend.domain.exceptions.DuplicateEmailException
import worklogtracker.backend.domain.repositories.UserRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.Email

class ExistingUserValidator(private val userRepository: UserRepositoryInterface) {
    
    suspend fun validate(email: Email) {
        val existingUser = userRepository.findByEmail(email)
        if (existingUser != null) {
            throw DuplicateEmailException(email.value)
        }
    }
}

