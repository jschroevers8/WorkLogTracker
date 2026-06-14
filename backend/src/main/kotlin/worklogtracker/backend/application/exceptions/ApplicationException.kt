package worklogtracker.backend.application.exceptions

sealed class ApplicationException(
    message: String,
) : Exception(message)
