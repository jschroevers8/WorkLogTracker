package worklogtracker.domain.exceptions
class FileSizeExceededException(size: Int) : DomainException("File size exceeds limit: $size bytes")
