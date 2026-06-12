package worklogtracker.backend.domain.exceptions
class AttachmentNotFoundException(attachmentId: String) : DomainException("Attachment not found: $attachmentId")
