package controller

import model.email
import service.sendEmail

fun send_email(
    senderEmail: String,
    password: String,
    recipient: String,
    subject: String, text: String,
    fileURLs: List<String>,
    fileNames: List<String>) {
    val Email = email(senderEmail, password, recipient, subject, text, fileURLs, fileNames)
    sendEmail(Email)
}




