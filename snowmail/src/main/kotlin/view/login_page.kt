package ca.uwaterloo.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.window.application

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.DragData
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import java.io.File
import kotlin.math.sin

import javax.imageio.ImageIO
import androidx.compose.ui.graphics.toComposeImageBitmap



@Composable
fun loginPage() {
    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
            Text(
                "Job Hunting: Tough, \n But you Do Not Have \nto Do It Alone!",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp))
            loginForm()
    }
}

@Composable
fun loginForm() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        loginWithAccount()
        Divider()
        // HorizontalDivider(thickness = 2.dp)
        loginWithGmail()
    }
}


@Composable
fun loginWithAccount() {
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

        Button(onClick = { onClick() }) {
            Text("Sign In")
        }
        Text("Didn't have an account? Register")
    }
}



@Composable
fun loginWithGmail() {
    Column(
       verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Or log in with")
        val iconpath = File("gmail_icon.png")

        val iconimage = ImageIO.read(iconpath)
        val iconbitmap = iconimage.toComposeImageBitmap()
        Button(onClick = { onClick() }) {
            Row (horizontalArrangement = Arrangement.Center){
                Image(iconbitmap, "gmail")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gmail")
            }

        }
    }
}

fun onClick() {

}
