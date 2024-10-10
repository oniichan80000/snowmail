package ca.uwaterloo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import javax.imageio.ImageIO


fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            SignUpPage()
        }
    }
}

val fullPageColor = 0xFFbad4db
val formColor = 0xFFFFFFFF
val sizeofInput = 0.1f

@Composable
fun SignUpPage() {
    Box(Modifier.fillMaxSize().background(Color(fullPageColor)), contentAlignment = Alignment.Center) {
        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Box(modifier = Modifier.fillMaxHeight(0.15f).background(Color(fullPageColor))) {
                Text(text = "Join Snowmail!", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color.Black,)
            }

            Spacer(modifier = Modifier.fillMaxHeight(0.01f))
            RegisterForm()
            Box(modifier = Modifier.fillMaxHeight(0.05f).background(Color(fullPageColor)))
        }

    }


}





@Composable
fun RegisterForm() {
    Box (Modifier.fillMaxWidth(0.7f).fillMaxHeight(0.8f).background(Color(formColor))) {
        Row {
            Column(Modifier.fillMaxWidth(0.1f)) { Box {} }
            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth(0.8f).fillMaxWidth()) {

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row(modifier = Modifier.fillMaxWidth(0.95f).fillMaxHeight(0.06f)) {
                    Column(Modifier.fillMaxWidth(0.53f)) { Text("First Name", textAlign = TextAlign.Start) }
                    Column { Text(text = "Last Name", textAlign = TextAlign.End) }
                }

                var firstName by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Row(Modifier.fillMaxWidth()) {
                    // first name input
                    Column(Modifier.fillMaxWidth(0.48f)) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            singleLine = true
                        )
                    }
                    Column(Modifier.fillMaxWidth(0.04f)) { Box{} }
                    // Spacer(modifier = Modifier.fillMaxWidth(0.04f))
                    // last name input
                    Column(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            singleLine = true
                        )
                    }
                }

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row { Text("Email Address") }
                // email address input
                Row (Modifier.fillMaxWidth()){ OutlinedTextField(value = email, onValueChange = { email = it }, singleLine = true, modifier = Modifier.fillMaxWidth()) }

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                // password input
                Row { Text("Password") }
                Row { OutlinedTextField(value = password, onValueChange = { password = it }, singleLine = true, modifier = Modifier.fillMaxWidth()) }

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row {
                    // register button
                    Button(onClick = { Register() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Register")
                    }
                }

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account?")
                    // navigate to login page
                    TextButton(onClick = { navigateLoginPage() }) {
                        Text("Sign in")
                    }
                }

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row { Divider() }

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){ Text(text = "Or register with") }


                // gmail register
                val iconfile = File("gmail_icon.png")
                val iconimage = ImageIO.read(iconfile)
                val iconbitmap = iconimage.toComposeImageBitmap()

                Row(modifier = Modifier.fillMaxHeight(0.03f)) {}
                Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                    Button(onClick = { GmailRegister() }) {
                        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Image(iconbitmap, "gmail")

                            Text("Register")
                        }
                    }
                }

            }
            Column(Modifier.fillMaxWidth(0.3f)) { Box {} }
        }
    }
}





fun Register() {}

fun navigateLoginPage() {}

fun GmailRegister() { }