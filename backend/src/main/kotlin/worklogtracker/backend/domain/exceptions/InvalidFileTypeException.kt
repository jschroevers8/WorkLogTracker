package worklogtracker.backend.domain.exceptions
class InvalidFileTypeException(fileName: String) : DomainException("File type not allowed: $fileName")
