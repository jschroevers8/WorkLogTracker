package worklogtracker.domain.exceptions
class InvalidPasswordException(message: String = "Password does not meet security requirements") : DomainException(message)
