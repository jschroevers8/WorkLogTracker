package worklogtracker.infrastructure.plugins

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.github.cdimascio.dotenv.dotenv
import worklogtracker.infrastructure.tables.AttachmentTable
import worklogtracker.infrastructure.tables.NotificationTable
import worklogtracker.infrastructure.tables.PendingSyncTable
import worklogtracker.infrastructure.tables.ProjectTable
import worklogtracker.infrastructure.tables.TaskTable
import worklogtracker.infrastructure.tables.TimerSessionTable
import worklogtracker.infrastructure.tables.UserTable
import worklogtracker.infrastructure.tables.WorkLogTable

fun initializeDatabase() {
    val dotenv =
        dotenv {
            directory = "backend"
            filename = ".env"
        }

    val useLocaleDatabase = dotenv["USE_LOCALE_DATABASE"]?.toBoolean() ?: false

    if (useLocaleDatabase) {
        Database.connect(
            url = dotenv["DB_URL"],
            driver = "com.mysql.cj.jdbc.Driver",
            user = dotenv["DB_USER"],
            password = dotenv["DB_PASSWORD"],
        )
    } else {
        Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver",
            user = "root",
            password = "",
        )
    }

    transaction {
        SchemaUtils.create(
            UserTable,
            ProjectTable,
            TaskTable,
            WorkLogTable,
            TimerSessionTable,
            NotificationTable,
            AttachmentTable,
            PendingSyncTable
        )
    }
}

