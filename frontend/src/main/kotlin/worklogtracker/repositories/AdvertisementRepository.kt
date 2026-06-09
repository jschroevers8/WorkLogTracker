package worklogtracker.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.KtorApi
import worklogtracker.data.remote.advertisement.AdvertisementResponse
import worklogtracker.data.remote.advertisement.CreateAdvertisement
import worklogtracker.data.remote.advertisement.CreateAdvertisementResponse

class AdvertisementRepository(
    private val api: ApiClient
) {

    private val baseUrl = "http://10.0.2.2:8080"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getAllAdvertisements(): List<AdvertisementResponse> {
        val response = api.get("$baseUrl/advertisement/all")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun getAdvertisementById(id: Int): AdvertisementResponse {
        val response = api.get("$baseUrl/advertisement/show/$id")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun createAdvertisement(advertisement: CreateAdvertisement): CreateAdvertisementResponse {
        val requestBody = json.encodeToString(advertisement)
        val response = api.post("$baseUrl/advertisement/create", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }
}
