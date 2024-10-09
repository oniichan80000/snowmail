package ca.uwaterloo.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.File
import javax.imageio.ImageIO


@Composable
fun SignUpPage() {
    Column {
        Text(text = "Join Snowmail!")
        RegisterForm()
    }

}


@Composable
fun RegisterForm() {
    Column {
        Row {
            Text(text = "First Name")
            Text(text = "Last Name")
        }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Row {
            // first name input
            OutlinedTextField(value = firstName, onValueChange = { firstName = it })

            // last name input
            OutlinedTextField(value = lastName, onValueChange = { lastName = it })
        }

        Text("Email Address")
        // email address input
        OutlinedTextField(value = email, onValueChange = { email = it })

        // password input
        Text("Password")
        OutlinedTextField(value = password, onValueChange = { password = it })

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





fun Register() {}

fun navigateLoginPage() {}

fun GmailRegister() { }