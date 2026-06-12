package worklogtracker.backend.domain.exceptions
class WorkLogNotFoundException(workLogId: String) : DomainException("WorkLog not found: $workLogId")
