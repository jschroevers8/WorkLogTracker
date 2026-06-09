package worklogtracker.dependencyinjection

import org.koin.core.module.dsl.*
import org.koin.dsl.module
import worklogtracker.data.auth.AuthManager
import worklogtracker.data.auth.AuthManagerInterface
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.KtorApi
import worklogtracker.presentation.user.account.AccountViewModel
import worklogtracker.presentation.user.login.LoginViewModel
import worklogtracker.presentation.user.notification.NotificationViewModel
import worklogtracker.presentation.user.signup.SignupViewModel
import worklogtracker.presentation.project.ProjectViewModel
import worklogtracker.presentation.task.TaskViewModel
import worklogtracker.presentation.worklog.WorkLogViewModel
import worklogtracker.repositories.ProjectRepository
import worklogtracker.repositories.TaskRepository
import worklogtracker.repositories.UserRepository
import worklogtracker.repositories.WorkLogRepository

//TODO misschien opsplitten?
val appModule = module {
    single { UserRepository(get()) }
    single { ProjectRepository(get()) }
    single { TaskRepository(get()) }
    single { WorkLogRepository(get()) }
    single<ApiClient> { KtorApi(get()) }
    single<AuthManagerInterface> { AuthManager(get()) }

    viewModel { AccountViewModel(get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { NotificationViewModel() }
    viewModel { ProjectViewModel(get()) }
    viewModel { TaskViewModel(get()) }
    viewModel { WorkLogViewModel(get(), get()) }
}
