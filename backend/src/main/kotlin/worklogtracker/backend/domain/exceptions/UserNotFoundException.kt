package worklogtracker.backend.domain.exceptions

class UserNotFoundException(
    userId: String,
) : DomainException("User not found: $userId")
