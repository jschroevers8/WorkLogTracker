package worklogtracker.domain.exceptions
class AttachmentNotFoundException(attachmentId: String) : DomainException("Attachment not found: $attachmentId")
