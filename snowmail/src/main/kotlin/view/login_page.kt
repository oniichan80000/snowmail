package ca.uwaterloo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import javax.imageio.ImageIO


@Composable
fun WebsitePage2() {
    var currentPage by remember { mutableStateOf("login") }

    when (currentPage) {
        "login" -> loginPage ({ currentPage = "signup" }, {currentPage = "homepage"})
        "signup" -> SignUpPage ({ currentPage = "login"}, { currentPage = "home"})
        "homepage" -> homePage()
    }
}


fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            WebsitePage2()
        }
    }
}



@Composable
fun loginPage(NavigateToSignup: () -> Unit, NavigateToHome: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Row {
            Text(
                "Job Hunting: ",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.Black
            )
            Text(
                "Tough",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color(0xff2b5dc7)
            )
        }
        Row {
            Text(
                "But you Do Not ",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.Black
            )
            Text(
                "Have",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color(0xff2b5dc7)
            )
        }
        Row {
            Text(
                "to Do It ",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.Black
            )
            Text(
                "Alone!",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color(0xff2b5dc7)
            )
        }


        loginForm(NavigateToSignup, NavigateToHome)
        }


    }


@Composable
fun loginForm(NavigateToSignup: () -> Unit, NavigateToHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        loginWithAccount(NavigateToSignup, NavigateToHome)
        Divider()
        // HorizontalDivider(thickness = 2.dp)
        loginWithGmail()
    }
}


@Composable
fun loginWithAccount(NavigateToSignup: () -> Unit, NavigateToHome: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(50.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        // email
        Text("Email Address", modifier = Modifier.align(Alignment.Start))
        var email by remember { mutableStateOf("") }
        OutlinedTextField(value = email, onValueChange = { email = it }, modifier = Modifier.fillMaxWidth().align(Alignment.Start), singleLine = true)

        // password
        Text("Password", modifier = Modifier.align(Alignment.Start))
        var password by remember { mutableStateOf("") }
        OutlinedTextField(value = password, onValueChange =  {password = it}, modifier = Modifier.fillMaxWidth().align(Alignment.Start), singleLine = true)



        // forgot your password?
        ClickableText(AnnotatedString("Forgot Your Password?"), onClick = {})

        // potential error message shown
        var errorMessage by remember { mutableStateOf("") }
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp))
        }

        // sign in button

        val SUCCESS = false
        val EMAILDOESNOTEXIST = false
        Button(onClick = {
            // if successful, navigate to home page
            if (SUCCESS) {
                NavigateToHome()
            }
            // if email doesn't exist, show and clear
            else if (EMAILDOESNOTEXIST) {
                errorMessage = "Email does not exist, please register first"
                email = ""
                password = ""
            }
            // if password and email does not match
            else {
                errorMessage = "Email and password do not match, please try again"
                email = ""
                password = ""
            }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor))) {
            Text("Sign In", color = Color.White)
        }



        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?")
            // navigate to login page
            TextButton(onClick = { navigateLoginPage(NavigateToSignup) }) {
                Text("Sign up", color = Color(buttonColor))
            }
        }
    }
}



@Composable
fun loginWithGmail() {
    Column(
       verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Or log in with")
        val iconfile = File("gmail_icon.png")

        val iconimage = ImageIO.read(iconfile)
        val iconbitmap = iconimage.toComposeImageBitmap()
        Button(onClick = { LoginWithGmail() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(formColor))) {
            Row (horizontalArrangement = Arrangement.Center){
                Image(iconbitmap, "gmail")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gmail", color = Color.Black)
            }

        }
    }
}



fun LoginWithGmail() {

}


