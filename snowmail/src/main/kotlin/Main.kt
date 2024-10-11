package ca.uwaterloo

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ca.uwaterloo.view.SignUpPage
import ca.uwaterloo.view.homePage
import ca.uwaterloo.view.loginPage

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            WebsitePage()
        }
    }
}


@Composable
fun WebsitePage() {

    var currentPage by remember { mutableStateOf("login") }

    when (currentPage) {
        "login" -> loginPage ({ currentPage = "signup" }, {currentPage = "homepage"})
        "signup" -> SignUpPage ({ currentPage = "login"}, { currentPage = "home"})
        "homepage" -> homePage()
    }
}
