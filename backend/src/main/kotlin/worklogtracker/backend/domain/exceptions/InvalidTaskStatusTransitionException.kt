package worklogtracker.backend.domain.exceptions

class InvalidTaskStatusTransitionException(
    message: String,
) : DomainException(message)
