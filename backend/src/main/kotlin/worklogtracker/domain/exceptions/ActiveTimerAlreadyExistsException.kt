package worklogtracker.domain.exceptions
class ActiveTimerAlreadyExistsException(userId: String) : DomainException("User $userId already has an active timer")
