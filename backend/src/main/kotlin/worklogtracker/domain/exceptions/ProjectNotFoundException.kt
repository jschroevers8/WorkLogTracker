package worklogtracker.domain.exceptions
class ProjectNotFoundException(projectId: String) : DomainException("Project not found: $projectId")
