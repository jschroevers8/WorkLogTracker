package worklogtracker.frontend.presentation.framework.annotations

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Required(
    val message: String = "This field cannot be empty",
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Email(
    val message: String = "Invalid email address",
)
