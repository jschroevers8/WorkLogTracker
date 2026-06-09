package worklogtracker.repositories

import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.KtorApi
import worklogtracker.data.remote.car.CreateCar
import worklogtracker.data.remote.car.CarResponse
import worklogtracker.data.remote.car.UpdateCar
import worklogtracker.data.remote.car.RecommendedCarResponse

class CarRepository(
    private val api: ApiClient
) {
    private val baseUrl = "http://10.0.2.2:8080"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun createCar(car: CreateCar): CarResponse {
        val requestBody = json.encodeToString(car)
        val response = api.post("$baseUrl/car/create", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun updateCar(car: UpdateCar): CarResponse {
        val carId = car.id

        val requestBody = json.encodeToString(car)
        val response = api.post("$baseUrl/car/update/$carId", requestBody)
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun getPersonalCars(): List<CarResponse> {
        val response = api.get("$baseUrl/personal/cars")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun getCarById(id: Int): CarResponse {
        val response = api.get("$baseUrl/car/show/$id")
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun getRecommendedCar(message: String): RecommendedCarResponse {
        val response = api.post("$baseUrl/car/recommend", message)
        return json.decodeFromString(response.bodyAsText())
    }
}
