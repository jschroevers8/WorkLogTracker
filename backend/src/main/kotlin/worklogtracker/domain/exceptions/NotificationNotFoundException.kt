package worklogtracker.domain.exceptions
class NotificationNotFoundException(notificationId: String) : DomainException("Notification not found: $notificationId")
