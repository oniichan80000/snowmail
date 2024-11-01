package ca.uwaterloo.controller

//import ca.uwaterloo.persistence.DBStorage
import kotlinx.coroutines.runBlocking

import integration.SupabaseClient

class SignUpController(private val dbStorage: SupabaseClient) {

    // Sign up a new user and return either userId or error message
    fun signUpUser(email: String, password: String, firstname: String, lastname: String): Result<String> {
        return runBlocking {
            dbStorage.authRepository.signUpUser(email, password, firstname, lastname)
        }
    }
}

// Testing SignUpController
fun main() {
    val dbStorage = SupabaseClient()
    val signUpController = SignUpController(dbStorage)

    // Test user registration
    val email = "wrw040613@gmail.com"
    val password = "sssssss"
    val firstname = "Cherry"
    val lastname = "Wang"

    println (signUpController.signUpUser(email, password, firstname, lastname))
}