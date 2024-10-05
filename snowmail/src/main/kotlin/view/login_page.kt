package ca.uwaterloo.view

import androidx.compose.ui.window.application


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.DragData
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


@Composable
fun loginPage() {
    Column (
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Job Hunting: Tough, \n But you Do Not Have \nto Do It Alone!")
            loginForm()
    }
}

@Composable
fun loginForm() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally

    ) {
        loginWithAccount()
        loginWithGmail()
    }
}


@Composable
fun loginWithAccount() {
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text("Email Address")
        OutlinedTextField(value = "email", onValueChange = {})
        Text("Password")
        OutlinedTextField(value = "email", onValueChange = {})
        Text("Forgot Your Password?")
        Button(onClick = { onClick() }) {
            Text("Sign In")
        }
        Text("Didn't have an account? Register")
    }
}



@Composable
fun loginWithGmail() {
    Column(modifier = Modifier.fillMaxSize(),
       verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Or log in with")
        Button(onClick = { onClick() }) {
            Text("Gmail")
        }
    }
}

fun onClick() {

}
