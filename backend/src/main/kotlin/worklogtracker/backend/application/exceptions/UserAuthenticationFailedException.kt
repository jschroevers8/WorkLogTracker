package worklogtracker.backend.application.exceptions

class UserAuthenticationFailedException(
    message: String = "User authentication failed",
) : ApplicationException(message)
