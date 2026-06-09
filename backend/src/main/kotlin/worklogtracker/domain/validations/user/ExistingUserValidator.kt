package worklogtracker.domain.validations.user

import worklogtracker.domain.exceptions.DuplicateEmailException
import worklogtracker.domain.repositories.UserRepositoryInterface
import worklogtracker.domain.valueobjects.user.Email

class ExistingUserValidator(private val userRepository: UserRepositoryInterface) {
    
    suspend fun validate(email: Email) {
        val existingUser = userRepository.findByEmail(email)
        if (existingUser != null) {
            throw DuplicateEmailException(email.value)
        }
    }
}

