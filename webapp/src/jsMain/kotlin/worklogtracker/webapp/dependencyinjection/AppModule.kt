package worklogtracker.webapp.dependencyinjection

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import worklogtracker.webapp.ApiClient
import worklogtracker.webapp.viewmodel.*

val appModule =
    module {
        singleOf(::ApiClient)

        factory { DashboardViewModel(get()) }
        factory { EmployeesViewModel(get()) }
        factory { (id: Long) -> EmployeeDetailViewModel(id, get()) }
        factory { (scope: kotlinx.coroutines.CoroutineScope) -> ProjectsViewModel(get(), scope) }
        factory { (id: Int) -> ProjectDetailViewModel(id, get()) }
        factory { (scope: kotlinx.coroutines.CoroutineScope, onLoginSuccess: (worklogtracker.shared.dto.auth.AuthResponse) -> Unit) ->
            LoginViewModel(get(), scope, onLoginSuccess)
        }
    }
