package worklogtracker.data.remote

import io.ktor.client.statement.HttpResponse

interface ApiClient {
    suspend fun get(url: String): HttpResponse
    suspend fun postWithoutBody(url: String): HttpResponse
    suspend fun post(url: String, body: String): HttpResponse
}

