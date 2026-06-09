package worklogtracker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import worklogtracker.dependencyinjection.appModule

class WorkLogTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WorkLogTrackerApp)
            modules(appModule)
        }
    }
}