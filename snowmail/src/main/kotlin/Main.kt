package ca.uwaterloo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ca.uwaterloo.view.loginPage


fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            loginPage()
        }
    }
}