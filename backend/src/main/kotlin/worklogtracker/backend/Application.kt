package worklogtracker.backend

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import worklogtracker.backend.infrastructure.plugins.configureRouting
import worklogtracker.backend.infrastructure.plugins.configureSecurity
import worklogtracker.backend.infrastructure.plugins.configureStatusPages
import worklogtracker.backend.infrastructure.plugins.initializeDatabase

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    initializeDatabase()
    configureSecurity()
    configureStatusPages()
    configureRouting()
}
