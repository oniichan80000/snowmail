package ca.uwaterloo.view



import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import model.UserInput
import model.UserProfile
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
//import kotlinx.serialization.Serializable

import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window
import service.EmailGenerationService
import integration.OpenAIClient
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun EmailGenerationPage() {
//    val client = HttpClient(CIO) {
//        install(ContentNegotiation) {
//            json()
//        }
//    }
    var emailContent by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    val httpClient = HttpClient(CIO)
    val openAIClient = OpenAIClient(httpClient)

    // Create an instance of EmailGenerationService
    val emailGenerationService = EmailGenerationService(openAIClient)

    // user input values
    var companyInput by remember { mutableStateOf("company name") }
    var descriptionInput by remember { mutableStateOf("description") }
    var recruiterNameInput by remember { mutableStateOf("recruiter name") }

    var userInput = UserInput(
        jobDescription = descriptionInput,
        recruiterEmail = "recruiter@example.com",
        jobTitle = "Software Engineer",
        company = companyInput,
        recruiterName = recruiterNameInput
    )

    val userProfile = UserProfile(
        userId = "123",
        firstName = "John",
        lastName = "Doe",
        skills = listOf("Java", "Kotlin", "SQL")
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray), // Optional: Add background color for better visibility
            //.height(50.dp), // Adjust height as needed
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("this is the navigation bar", style = MaterialTheme.typography.h6)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) { // this is the row for user input forms and content
        Column(
            modifier = Modifier
            .fillMaxWidth(0.6f)  // 60% width
            .padding(16.dp)) {
            TextField(
                value = companyInput,
                onValueChange = { companyInput = it },
                label = { Text("Company") },
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            )
            TextField(value = descriptionInput, onValueChange = { descriptionInput = it }, label = { Text("Job Description") })
        }
        TextField(value = recruiterNameInput, onValueChange = { recruiterNameInput = it }, label = { Text("Recruiter Name") })
    }

    Row() { // this is the row for button
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = emailContent, modifier = Modifier.padding(16.dp))
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
//                try {
//                    val response: HttpResponse = client.post("http://localhost:8080/generate-email") {
//                        contentType(ContentType.Application.Json)
//                        setBody(Json.encodeToString(userInput) + Json.encodeToString(userProfile))
//                    }
//                    if (response.status == HttpStatusCode.OK) {
//                        val generatedEmail = response.bodyAsText()
//                        emailContent = generatedEmail
//                        showDialog = true
//                    } else {
//                        emailContent = "Failed to generate email: ${response.status}"
//                    }
//                } catch (e: Exception) {
//                    emailContent = "Error: ${e.message}"
//                }
                try {
                    val generatedEmail = emailGenerationService.generateEmail(userInput, userProfile)
                    println("Generated Email: ${generatedEmail.body}")
                    emailContent = generatedEmail.body ?: "Failed to generate email"
                    showDialog = true
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }) {
            Text("Generate Email")
        }
    }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Generated Email") },
                text = { Text(emailContent) },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            EmailGenerationPage()
        }
    }
}