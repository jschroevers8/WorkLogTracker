package worklogtracker.backend.infrastructure.plugins

import worklogtracker.backend.application.usecases.auth.LoginUserUseCase
import worklogtracker.backend.application.usecases.auth.RegisterUserUseCase
import worklogtracker.backend.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.backend.application.usecases.notification.GetUserNotificationsUseCase
import worklogtracker.backend.application.usecases.notification.MarkNotificationAsReadUseCase
import worklogtracker.backend.application.usecases.project.CreateProjectUseCase
import worklogtracker.backend.application.usecases.project.ListProjectsUseCase
import worklogtracker.backend.application.usecases.project.UpdateProjectUseCase
import worklogtracker.backend.application.usecases.task.AssignTaskUseCase
import worklogtracker.backend.application.usecases.task.CreateTaskUseCase
import worklogtracker.backend.application.usecases.task.ListTasksUseCase
import worklogtracker.backend.application.usecases.task.UpdateTaskStatusUseCase
import worklogtracker.backend.application.usecases.task.UploadTaskPhotoUseCase
import worklogtracker.backend.application.usecases.task.RecordTaskLocationUseCase
import worklogtracker.backend.application.usecases.worklog.LogTimeUseCase
import worklogtracker.backend.application.usecases.worklog.GetUserWorkLogsUseCase
import worklogtracker.backend.domain.factories.NotificationFactory
import worklogtracker.backend.domain.factories.ProjectFactory
import worklogtracker.backend.domain.factories.TaskFactory
import worklogtracker.backend.domain.factories.UserFactory
import worklogtracker.backend.domain.validations.user.ExistingUserValidator
import worklogtracker.backend.infrastructure.auth.JwtTokenGenerator
import worklogtracker.backend.infrastructure.repositories.NotificationRepository
import worklogtracker.backend.infrastructure.repositories.ProjectRepository
import worklogtracker.backend.infrastructure.repositories.TaskRepository
import worklogtracker.backend.infrastructure.repositories.TaskAssignmentRepository
import worklogtracker.backend.infrastructure.repositories.TimeEntryRepository
import worklogtracker.backend.infrastructure.repositories.TaskPhotoRepository
import worklogtracker.backend.infrastructure.repositories.TaskLocationRepository
import worklogtracker.backend.infrastructure.repositories.UserRepository
import worklogtracker.backend.presentation.auth.loginRoute
import worklogtracker.backend.presentation.auth.registerRoute
import worklogtracker.backend.presentation.notification.getNotificationsRoute
import worklogtracker.backend.presentation.notification.markNotificationReadRoute
import worklogtracker.backend.presentation.project.createProjectRoute
import worklogtracker.backend.presentation.project.getProjectsRoute
import worklogtracker.backend.presentation.project.updateProjectRoute
import worklogtracker.backend.presentation.report.getProjectReportRoute
import worklogtracker.backend.presentation.report.getUserReportRoute
import worklogtracker.backend.presentation.task.assignTaskRoute
import worklogtracker.backend.presentation.task.createTaskRoute
import worklogtracker.backend.presentation.task.getTasksRoute
import worklogtracker.backend.presentation.task.updateTaskStatusRoute
import worklogtracker.backend.presentation.task.uploadTaskPhotoRoute
import worklogtracker.backend.presentation.task.recordTaskLocationRoute
import worklogtracker.backend.presentation.worklog.logTimeRoute
import worklogtracker.backend.presentation.worklog.getWorkLogsRoute
import worklogtracker.backend.presentation.routes.user.getUsersRoute
import io.ktor.server.application.*
import io.ktor.server.routing.routing

import worklogtracker.backend.application.usecases.ai.GenerateAiDescriptionUseCase
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureRouting() {
    val httpClient = HttpClient(CIO)
    // Client configuration is moved to Use Case to avoid import conflicts

    val jwtSecret = environment.config.property("jwt.secret").getString()

    val userRepository = UserRepository()
    val projectRepository = ProjectRepository()
    val taskRepository = TaskRepository()
    val taskAssignmentRepository = TaskAssignmentRepository()
    val timeEntryRepository = TimeEntryRepository()
    val taskPhotoRepository = TaskPhotoRepository()
    val taskLocationRepository = TaskLocationRepository()
    val notificationRepository = NotificationRepository()
    
    val userFactory = UserFactory()
    val projectFactory = ProjectFactory()
    val taskFactory = TaskFactory()
    val notificationFactory = NotificationFactory()
    
    val existingUserValidator = ExistingUserValidator(userRepository)
    val tokenGenerator = JwtTokenGenerator(jwtSecret)
    
    val registerUseCase = RegisterUserUseCase(userRepository, existingUserValidator, userFactory, tokenGenerator)
    val loginUseCase = LoginUserUseCase(userRepository, tokenGenerator)
    val createProjectUseCase = CreateProjectUseCase(projectRepository, userRepository, projectFactory)
    val updateProjectUseCase = UpdateProjectUseCase(projectRepository, userRepository)
    val listProjectsUseCase = ListProjectsUseCase(projectRepository, userRepository)
    val createNotificationUseCaseInstance = CreateNotificationUseCase(notificationRepository, notificationFactory)
    val createTaskUseCase = CreateTaskUseCase(taskRepository, taskAssignmentRepository, userRepository, taskFactory, createNotificationUseCaseInstance)
    val assignTaskUseCase = AssignTaskUseCase(taskRepository, taskAssignmentRepository, userRepository, createNotificationUseCaseInstance)
    val updateTaskStatusUseCase = UpdateTaskStatusUseCase(taskRepository, createNotificationUseCaseInstance)
    val listTasksUseCase = ListTasksUseCase(taskRepository)
    
    val generateAiDescriptionUseCase = GenerateAiDescriptionUseCase(httpClient)
    val logTimeUseCase = LogTimeUseCase(timeEntryRepository, taskAssignmentRepository, taskRepository, generateAiDescriptionUseCase)
    val uploadTaskPhotoUseCase = UploadTaskPhotoUseCase(taskPhotoRepository)
    val recordTaskLocationUseCase = RecordTaskLocationUseCase(taskLocationRepository)
    val getUserWorkLogsUseCase = GetUserWorkLogsUseCase(timeEntryRepository)
    
    val getUserNotificationsUseCase = GetUserNotificationsUseCase(notificationRepository)
    val markNotificationAsReadUseCase = MarkNotificationAsReadUseCase(notificationRepository)

    routing {
        registerRoute(registerUseCase)
        loginRoute(loginUseCase)
        getProjectsRoute(listProjectsUseCase)
        createProjectRoute(createProjectUseCase)
        updateProjectRoute(updateProjectUseCase)
        getTasksRoute(listTasksUseCase)
        createTaskRoute(createTaskUseCase)
        updateTaskStatusRoute(updateTaskStatusUseCase)
        assignTaskRoute(assignTaskUseCase)
        uploadTaskPhotoRoute(uploadTaskPhotoUseCase)
        recordTaskLocationRoute(recordTaskLocationUseCase)
        logTimeRoute(logTimeUseCase)
        getWorkLogsRoute(getUserWorkLogsUseCase)
        getNotificationsRoute(getUserNotificationsUseCase)
        markNotificationReadRoute(markNotificationAsReadUseCase)
        getUsersRoute(userRepository)
    }
}