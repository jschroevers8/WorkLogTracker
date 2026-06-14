package worklogtracker.backend.domain.exceptions

class ProjectNotFoundException(
    projectId: String,
) : DomainException("Project not found: $projectId")
