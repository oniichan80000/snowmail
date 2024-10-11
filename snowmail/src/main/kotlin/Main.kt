package ca.uwaterloo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ca.uwaterloo.view.SignUpPage
import ca.uwaterloo.view.homePage
import ca.uwaterloo.view.loginPage
import ca.uwaterloo.view.WelcomePage

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            WebsitePage()
        }
    }
}


@Composable
fun WebsitePage() {

    var currentPage by remember { mutableStateOf("welcome") }

    when (currentPage) {
        "login" -> loginPage ({ currentPage = "signup" }, {currentPage = "homepage"})
        "signup" -> SignUpPage ({ currentPage = "login"}, { currentPage = "homepage"})
        "welcome" -> WelcomePage ({ currentPage = "signup"}, {currentPage = "login"})
        "homepage" -> homePage()
    }
}


