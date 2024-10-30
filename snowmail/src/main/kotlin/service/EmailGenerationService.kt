package service


import integration.OpenAIClient

import model.UserInput
import model.GeneratedEmail

class EmailGenerationService(private val openAIClient: OpenAIClient) {

     suspend fun generateEmail(userInput: UserInput): GeneratedEmail {
          val cleanedInput = cleanInput(userInput)

          return try {
               openAIClient.generateEmail(cleanedInput)
          } catch (e: Exception) {
               // Handle exceptions and return a meaningful error response
               throw RuntimeException("Failed to generate email: ${e.message}")
          }
     }

     private fun cleanInput(userInput: UserInput): UserInput {
          // Implement input cleaning logic here
          return userInput
     }

}
