package worklogtracker.backend.domain.validations.timer

import worklogtracker.backend.domain.exceptions.ActiveTimerAlreadyExistsException
import worklogtracker.backend.domain.repositories.TimerSessionRepositoryInterface
import worklogtracker.backend.domain.valueobjects.user.UserId

class ActiveTimerValidator(private val timerRepository: TimerSessionRepositoryInterface) {
    
    suspend fun validate(userId: UserId) {
        val activeTimer = timerRepository.findActiveByUser(userId)
        if (activeTimer != null) {
            throw ActiveTimerAlreadyExistsException(userId.value.toString())
        }
    }
}

