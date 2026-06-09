package worklogtracker.repositories

import io.ktor.client.statement.bodyAsText
import worklogtracker.data.remote.KtorApi
import kotlinx.serialization.json.Json
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.rental.CreateRental
import worklogtracker.data.remote.rental.RegisterRentalTrip
import worklogtracker.data.remote.rental.RentalResponse
import worklogtracker.data.remote.rental.RentalTripResponse

class RentalRepository(
    private val api: ApiClient
) {

    private val baseUrl = "http://10.0.2.2:8080"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun createRental(rental: CreateRental): RentalResponse {
        val requestBody = json.encodeToString(rental)
        val response = api.post("$baseUrl/rent/advertisement", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun getUserRentals(): List<RentalResponse> {
        val response = api.get("$baseUrl/rental/user")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun registerRentalTrip(trip: RegisterRentalTrip): RentalTripResponse {
        val body = json.encodeToString(trip)
        val response = api.post("$baseUrl/rental/trip/register", body)
        return json.decodeFromString(response.bodyAsText())
    }
}
