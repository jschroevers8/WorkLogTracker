package worklogtracker.plugins.koin

import org.koin.core.module.dsl.*
import org.koin.dsl.module
import worklogtracker.data.local.AuthManager
import worklogtracker.data.local.AuthManagerInterface
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.KtorApi
import worklogtracker.presentation.user.account.AccountViewModel
import worklogtracker.presentation.user.address.AddressViewModel
import worklogtracker.presentation.user.login.LoginViewModel
import worklogtracker.presentation.user.signup.SignupViewModel
import worklogtracker.repositories.ProjectRepository
import worklogtracker.repositories.TaskRepository
import worklogtracker.repositories.UserRepository
import worklogtracker.repositories.WorkLogRepository

val appModule = module {
    single { UserRepository(get()) }
    single { ProjectRepository(get()) }
    single { TaskRepository(get()) }
    single { WorkLogRepository(get()) }
    single<ApiClient> { KtorApi(get()) }
    single<AuthManagerInterface> { AuthManager(get()) }

    viewModel { AccountViewModel(get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
}
