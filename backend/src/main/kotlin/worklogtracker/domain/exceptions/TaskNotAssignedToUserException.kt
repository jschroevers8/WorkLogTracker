package worklogtracker.domain.exceptions
class TaskNotAssignedToUserException(userId: String, taskId: String) : DomainException("Task $taskId is not assigned to user $userId")
