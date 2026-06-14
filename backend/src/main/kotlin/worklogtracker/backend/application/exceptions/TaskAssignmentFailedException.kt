package worklogtracker.backend.application.exceptions

class TaskAssignmentFailedException(
    message: String = "Failed to assign task",
) : ApplicationException(message)
