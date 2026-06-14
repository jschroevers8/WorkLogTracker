package worklogtracker.backend.domain.exceptions

class DuplicateEmailException(
    email: String,
) : DomainException("Email already registered: $email")
