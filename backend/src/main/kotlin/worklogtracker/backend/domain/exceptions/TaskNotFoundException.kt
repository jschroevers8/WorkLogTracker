package worklogtracker.backend.domain.exceptions

class TaskNotFoundException(
    taskId: String,
) : DomainException("Task not found: $taskId")
