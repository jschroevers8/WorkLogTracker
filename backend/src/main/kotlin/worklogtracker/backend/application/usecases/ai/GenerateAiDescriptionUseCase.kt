package worklogtracker.backend.application.usecases.ai

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class OllamaResponse(
    val response: String
)

class GenerateAiDescriptionUseCase(
    private val client: HttpClient
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val ollamaUrl = "http://localhost:11434/api/generate"

    suspend operator fun invoke(title: String, description: String?): String? {
        return try {
            val prompt = """
                Maak een korte, professionele samenvatting van de volgende taak voor in een urenregistratiesysteem.
                Taak titel: $title
                Gebruiker beschrijving: ${description ?: "Geen beschrijving opgegeven"}
                
                De samenvatting moet zakelijk en beknopt zijn.
            """.trimIndent()

            val httpResponse: HttpResponse = client.post(ollamaUrl) {
                contentType(ContentType.Application.Json)
                setBody(buildJsonObject {
                    put("model", "llama3")
                    put("prompt", prompt)
                    put("stream", false)
                }.toString())
            }

            if (httpResponse.status == HttpStatusCode.OK) {
                val bodyText = httpResponse.bodyAsText()
                val responseObj = json.decodeFromString<OllamaResponse>(bodyText)
                responseObj.response.trim()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Ollama AI Fout: ${e.message}")
            null
        }
    }
}
