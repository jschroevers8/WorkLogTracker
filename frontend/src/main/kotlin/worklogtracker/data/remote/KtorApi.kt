package worklogtracker.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import worklogtracker.data.local.AuthManagerInterface
import kotlin.coroutines.cancellation.CancellationException

class KtorApi(private val authManager: AuthManagerInterface) : ApiClient {

    private val client = HttpClient(CIO) {
        expectSuccess = false

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    authManager.clearAuthToken()
                }
            }
        }
    }

    private suspend fun getAuthToken() = authManager.authTokenFlow.first()

    override suspend fun get(url: String): HttpResponse {
        val token = getAuthToken()
        val response = client.get(url) {
            if (token != null) header("Authorization", "Bearer $token")
        }
        if (response.status == HttpStatusCode.Unauthorized) throw CancellationException()
        return response
    }

    override suspend fun postWithoutBody(url: String): HttpResponse {
        val token = getAuthToken()
        val response = client.post(url) {
            if (token != null) header("Authorization", "Bearer $token")
        }
        if (response.status == HttpStatusCode.Unauthorized) throw CancellationException()
        return response
    }

    override suspend fun post(url: String, body: String): HttpResponse {
        val token = getAuthToken()
        val response = client.post(url) {
            setBody(body)
            contentType(io.ktor.http.ContentType.Application.Json)
            if (token != null) header("Authorization", "Bearer $token")
        }
        if (response.status == HttpStatusCode.Unauthorized) throw CancellationException()
        return response
    }
}
