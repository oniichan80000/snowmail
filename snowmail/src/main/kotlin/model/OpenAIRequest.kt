package model

import kotlinx.serialization.Serializable

@Serializable
data class OpenAIRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int
)