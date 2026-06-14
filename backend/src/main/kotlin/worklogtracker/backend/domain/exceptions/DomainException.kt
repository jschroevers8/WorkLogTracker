package worklogtracker.backend.domain.exceptions

sealed class DomainException(
    message: String,
) : Exception(message)
