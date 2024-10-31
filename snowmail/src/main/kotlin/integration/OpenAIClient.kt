package integration

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

import io.ktor.http.*


import kotlinx.serialization.json.Json
import model.UserInput
import model.GeneratedEmail
import model.OpenAIRequest

class OpenAIClient(private val httpClient: HttpClient) {

    suspend fun generateEmail(userInput: UserInput): GeneratedEmail {
        val prompt = buildPrompt(userInput)

        val request = OpenAIRequest(
            model = "text-davinci-003",
            prompt = prompt,
            max_tokens = 150
        )

        val response: HttpResponse = try {
            httpClient.post("https://api.openai.com/v1/completions") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer YOUR_OPENAI_API_KEY")
                setBody(Json.encodeToString(OpenAIRequest.serializer(), request))
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to call OpenAI API: ${e.message}")
        }

        val responseBody: String = response.bodyAsText()
        return parseGeneratedText(responseBody)
    }

    private fun buildPrompt(userInput: UserInput): String {
        return """
            Job Description: ${userInput.jobDescription}
        """.trimIndent()
    }

    private fun parseGeneratedText(responseBody: String): GeneratedEmail {
        // Implement the logic to parse the response body and extract the email content
        // This is a placeholder implementation
        return GeneratedEmail(
            subject = "Generated Subject",
            body = "Generated Body"
        )
    }
}
