package worklogtracker.infrastructure.plugins

import worklogtracker.application.usecases.auth.LoginUserUseCase
import worklogtracker.application.usecases.auth.RegisterUserUseCase
import worklogtracker.application.usecases.notification.CreateNotificationUseCase
import worklogtracker.application.usecases.notification.GetUserNotificationsUseCase
import worklogtracker.application.usecases.notification.MarkNotificationAsReadUseCase
import worklogtracker.application.usecases.project.CreateProjectUseCase
import worklogtracker.application.usecases.project.ListProjectsUseCase
import worklogtracker.application.usecases.project.UpdateProjectUseCase
import worklogtracker.application.usecases.sync.AddPendingSyncUseCase
import worklogtracker.application.usecases.sync.GetPendingSyncsUseCase
import worklogtracker.application.usecases.task.AssignTaskUseCase
import worklogtracker.application.usecases.task.CreateTaskUseCase
import worklogtracker.application.usecases.task.GetUpcomingDeadlinesUseCase
import worklogtracker.application.usecases.task.ListTasksUseCase
import worklogtracker.application.usecases.task.UpdateTaskStatusUseCase
import worklogtracker.application.usecases.worklog.CreateManualWorkLogUseCase
import worklogtracker.application.usecases.worklog.GetUserWorkLogsUseCase
import worklogtracker.application.usecases.worklog.StartTimerUseCase
import worklogtracker.application.usecases.worklog.StopTimerUseCase
import worklogtracker.domain.factories.NotificationFactory
import worklogtracker.domain.factories.ProjectFactory
import worklogtracker.domain.factories.TaskFactory
import worklogtracker.domain.factories.TimerSessionFactory
import worklogtracker.domain.factories.UserFactory
import worklogtracker.domain.factories.WorkLogFactory
import worklogtracker.domain.validations.timer.ActiveTimerValidator
import worklogtracker.domain.validations.user.ExistingUserValidator
import worklogtracker.infrastructure.auth.JwtTokenGenerator
import worklogtracker.infrastructure.repositories.AttachmentRepository
import worklogtracker.infrastructure.repositories.NotificationRepository
import worklogtracker.infrastructure.repositories.PendingSyncRepository
import worklogtracker.infrastructure.repositories.ProjectRepository
import worklogtracker.infrastructure.repositories.TaskRepository
import worklogtracker.infrastructure.repositories.TimerSessionRepository
import worklogtracker.infrastructure.repositories.UserRepository
import worklogtracker.infrastructure.repositories.WorkLogRepository
import worklogtracker.presentation.routes.auth.loginRoute
import worklogtracker.presentation.routes.auth.registerRoute
import worklogtracker.presentation.routes.notification.getNotificationsRoute
import worklogtracker.presentation.routes.notification.markNotificationReadRoute
import worklogtracker.presentation.routes.project.createProjectRoute
import worklogtracker.presentation.routes.project.getProjectsRoute
import worklogtracker.presentation.routes.project.updateProjectRoute
import worklogtracker.presentation.routes.report.getProjectReportRoute
import worklogtracker.presentation.routes.report.getUserReportRoute
import worklogtracker.presentation.routes.sync.getPendingSyncsRoute
import worklogtracker.presentation.routes.sync.pushSyncRoute
import worklogtracker.presentation.routes.task.assignTaskRoute
import worklogtracker.presentation.routes.task.createTaskRoute
import worklogtracker.presentation.routes.task.getTasksRoute
import worklogtracker.presentation.routes.task.getUpcomingDeadlinesRoute
import worklogtracker.presentation.routes.task.updateTaskStatusRoute
import worklogtracker.presentation.routes.worklog.createWorkLogRoute
import worklogtracker.presentation.routes.worklog.getWorkLogsRoute
import worklogtracker.presentation.routes.worklog.startTimerRoute
import worklogtracker.presentation.routes.worklog.stopTimerRoute
import io.ktor.server.application.*
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    val jwtSecret = environment.config.property("jwt.secret").getString()

    val userRepository = UserRepository()
    val projectRepository = ProjectRepository()
    val taskRepository = TaskRepository()
    val workLogRepository = WorkLogRepository()
    val timerSessionRepository = TimerSessionRepository()
    val notificationRepository = NotificationRepository()
    val attachmentRepository = AttachmentRepository()
    val pendingSyncRepository = PendingSyncRepository()
    val userFactory = UserFactory()
    val projectFactory = ProjectFactory()
    val taskFactory = TaskFactory()
    val workLogFactory = WorkLogFactory()
    val timerSessionFactory = TimerSessionFactory()
    val notificationFactory = NotificationFactory()
    val existingUserValidator = ExistingUserValidator(userRepository)
    val activeTimerValidator = ActiveTimerValidator(timerSessionRepository)
    val tokenGenerator = JwtTokenGenerator(jwtSecret)
    val registerUseCase = RegisterUserUseCase(userRepository, existingUserValidator, userFactory, tokenGenerator)
    val loginUseCase = LoginUserUseCase(userRepository, tokenGenerator)
    val createProjectUseCase = CreateProjectUseCase(projectRepository, userRepository, projectFactory)
    val updateProjectUseCase = UpdateProjectUseCase(projectRepository, userRepository)
    val listProjectsUseCase = ListProjectsUseCase(projectRepository, userRepository)
    val createTaskUseCase = CreateTaskUseCase(taskRepository, userRepository, taskFactory)
    val assignTaskUseCase = AssignTaskUseCase(taskRepository, userRepository)
    val updateTaskStatusUseCase = UpdateTaskStatusUseCase(taskRepository)
    val listTasksUseCase = ListTasksUseCase(taskRepository)
    val getUpcomingDeadlinesUseCase = GetUpcomingDeadlinesUseCase(taskRepository)
    val startTimerUseCase =
        StartTimerUseCase(timerSessionRepository, taskRepository, activeTimerValidator, timerSessionFactory)
    val stopTimerUseCase = StopTimerUseCase(timerSessionRepository, workLogRepository, workLogFactory)
    val createManualWorkLogUseCase = CreateManualWorkLogUseCase(workLogRepository, taskRepository, workLogFactory)
    val getUserWorkLogsUseCase = GetUserWorkLogsUseCase(workLogRepository)
    val createNotificationUseCase = CreateNotificationUseCase(notificationRepository, notificationFactory)
    val getUserNotificationsUseCase = GetUserNotificationsUseCase(notificationRepository)
    val markNotificationAsReadUseCase = MarkNotificationAsReadUseCase(notificationRepository)
    val addPendingSyncUseCase = AddPendingSyncUseCase(pendingSyncRepository)
    val getPendingSyncsUseCase = GetPendingSyncsUseCase(pendingSyncRepository)


    routing {
        registerRoute(registerUseCase)
        loginRoute(loginUseCase)
        getProjectsRoute(listProjectsUseCase)
        createProjectRoute(createProjectUseCase)
        updateProjectRoute(updateProjectUseCase)
        getTasksRoute(listTasksUseCase)
        createTaskRoute(createTaskUseCase)
        getUpcomingDeadlinesRoute(getUpcomingDeadlinesUseCase)
        updateTaskStatusRoute(updateTaskStatusUseCase)
        assignTaskRoute(assignTaskUseCase)
        startTimerRoute(startTimerUseCase)
        stopTimerRoute(stopTimerUseCase)
        createWorkLogRoute(createManualWorkLogUseCase)
        getWorkLogsRoute(getUserWorkLogsUseCase)
        getNotificationsRoute(getUserNotificationsUseCase)
        markNotificationReadRoute(markNotificationAsReadUseCase)
        getPendingSyncsRoute(getPendingSyncsUseCase)
        pushSyncRoute(addPendingSyncUseCase)
        getUserReportRoute(workLogRepository)
        getProjectReportRoute(taskRepository)
    }
}