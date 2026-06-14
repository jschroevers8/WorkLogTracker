package worklogtracker.backend.application.exceptions

class TaskCreationFailedException(
    message: String = "Failed to create task",
) : ApplicationException(message)
