package worklogtracker.domain.validations.timer

import worklogtracker.domain.exceptions.ActiveTimerAlreadyExistsException
import worklogtracker.domain.repositories.TimerSessionRepositoryInterface
import worklogtracker.domain.valueobjects.user.UserId

class ActiveTimerValidator(private val timerRepository: TimerSessionRepositoryInterface) {
    
    suspend fun validate(userId: UserId) {
        val activeTimer = timerRepository.findActiveByUser(userId)
        if (activeTimer != null) {
            throw ActiveTimerAlreadyExistsException(userId.value.toString())
        }
    }
}

