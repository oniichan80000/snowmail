package ca.uwaterloo.controller

import ca.uwaterloo.model.UserProfile
import ca.uwaterloo.persistence.DBStorage
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate

class ProfileController(private val dbStorage: DBStorage) {

    // get user's name and display it on profile page
    suspend fun getUserName(userId: String): Result<String> {
        return dbStorage.userProfileRepository.getUserName(userId)
    }




}

fun main() = runBlocking<Unit> {
    val dbStorage = DBStorage()
    val profileController = ProfileController(dbStorage)

    val userId = "c9498eec-ac17-4a3f-8d91-61efba3f7277"
    val profileResult = profileController.getUserName(userId)

    profileResult.onSuccess { fullName ->
        println("User Profile Name: $fullName")
    }.onFailure { error ->
        println("Error fetching user profile: ${error.message}")
    }
}