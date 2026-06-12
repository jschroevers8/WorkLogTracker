package worklogtracker.frontend.dependencyinjection

import org.koin.core.module.dsl.*
import org.koin.dsl.module
import worklogtracker.frontend.data.auth.AuthManager
import worklogtracker.frontend.data.auth.AuthManagerInterface
import worklogtracker.frontend.data.remote.ApiClient
import worklogtracker.frontend.data.remote.KtorApi
import worklogtracker.frontend.presentation.user.account.AccountViewModel
import worklogtracker.frontend.presentation.user.login.LoginViewModel
import worklogtracker.frontend.presentation.user.notification.NotificationViewModel
import worklogtracker.frontend.presentation.user.signup.SignupViewModel
import worklogtracker.frontend.presentation.project.ProjectViewModel
import worklogtracker.frontend.presentation.task.TaskViewModel
import worklogtracker.frontend.presentation.worklog.WorkLogViewModel
import worklogtracker.frontend.repositories.ProjectRepository
import worklogtracker.frontend.repositories.TaskRepository
import worklogtracker.frontend.repositories.UserRepository
import worklogtracker.frontend.repositories.WorkLogRepository
import worklogtracker.frontend.repositories.NotificationRepository

//TODO misschien opsplitten?
val appModule = module {
    single { UserRepository(get()) }
    single { ProjectRepository(get()) }
    single { TaskRepository(get()) }
    single { WorkLogRepository(get()) }
    single { NotificationRepository(get()) }
    single<ApiClient> { KtorApi(get()) }
    single<AuthManagerInterface> { AuthManager(get()) }

    viewModel { AccountViewModel(get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { NotificationViewModel(get()) }
    viewModel { ProjectViewModel(get()) }
    viewModel { TaskViewModel(get()) }
    viewModel { WorkLogViewModel(get(), get()) }
}
