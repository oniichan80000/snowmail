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



@Composable
fun SignUpPage() {
    Box(Modifier.fillMaxSize().background(Color(0xb3bcc9))) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Text(text = "Join Snowmail!", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            RegisterForm()
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        }

    }


}


@Composable
fun RegisterForm() {
    Box (Modifier.fillMaxWidth(0.7f).background(Color(0xf9fafc))) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.fillMaxWidth()) {
                Row {
                    Text("First Name", textAlign = TextAlign.Start)
                    Text(text = "Last Name", textAlign = TextAlign.End)
                }
            }

            var firstName by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Row {
                // first name input
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, singleLine = true)

                // last name input
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, singleLine = true)
            }

            Text("Email Address")
            // email address input
            OutlinedTextField(value = email, onValueChange = { email = it }, singleLine = true)

            // password input
            Text("Password")
            OutlinedTextField(value = password, onValueChange = { password = it }, singleLine = true)

            // register button
            Button(onClick = { Register() }) {
                Text("Register")
            }

            Row {
                Text("Already have an account?")
                // navigate to login page
                TextButton(onClick = { navigateLoginPage() }) {
                    Text("Sign in")
                }
            }

            Divider()
            Text(text = "Or register with")


            // gmail register
            val iconfile = File("gmail_icon.png")
            val iconimage = ImageIO.read(iconfile)
            val iconbitmap = iconimage.toComposeImageBitmap()

            Button(onClick = { GmailRegister() }) {
                Row {
                    Image(iconbitmap, "gmail")

                    Text("Register")
                }
            }

        }
    }
}





fun Register() {}

fun navigateLoginPage() {}

fun GmailRegister() { }