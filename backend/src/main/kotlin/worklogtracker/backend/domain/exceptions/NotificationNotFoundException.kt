package worklogtracker.backend.domain.exceptions

class NotificationNotFoundException(
    notificationId: String,
) : DomainException("Notification not found: $notificationId")
