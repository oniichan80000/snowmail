package model

import kotlinx.serialization.Serializable

@Serializable
data class UserInput(
    val jobDescription: String,
    val recreuiterEmail: String,
)
