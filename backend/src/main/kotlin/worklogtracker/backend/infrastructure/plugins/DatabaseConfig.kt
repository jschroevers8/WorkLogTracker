package worklogtracker.backend.infrastructure.plugins

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import worklogtracker.backend.infrastructure.tables.NotificationTable
import worklogtracker.backend.infrastructure.tables.ProjectTable
import worklogtracker.backend.infrastructure.tables.TaskAssignmentTable
import worklogtracker.backend.infrastructure.tables.TaskLocationTable
import worklogtracker.backend.infrastructure.tables.TaskPhotoTable
import worklogtracker.backend.infrastructure.tables.TaskTable
import worklogtracker.backend.infrastructure.tables.TimeEntryTable
import worklogtracker.backend.infrastructure.tables.UserTable

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
        SchemaUtils.createMissingTablesAndColumns(
            UserTable,
            ProjectTable,
            TaskTable,
            TaskAssignmentTable,
            TimeEntryTable,
            TaskPhotoTable,
            TaskLocationTable,
            NotificationTable,
        )
    }
}
