package worklogtracker.webapp

import org.jetbrains.compose.web.renderComposable
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import worklogtracker.webapp.dependencyinjection.appModule
import worklogtracker.webapp.navigation.AppNavigator

fun main() {
    startKoin {
        modules(appModule)
    }

    renderComposable(rootElementId = "root") {
        KoinContext {
            AppNavigator()
        }
    }
}
