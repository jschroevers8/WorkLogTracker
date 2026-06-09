package worklogtracker.domain.exceptions
class UserNotFoundException(userId: String) : DomainException("User not found: $userId")
