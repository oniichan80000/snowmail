package service

import model.email
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


// CONSTANTS
val host = "smtp.gmail.com"
val port = 587


fun sendEmail(email: email) {

    // properties
    val properties = Properties().apply {
        put("mail.smtp.auth", "true")
        put("mail.smtp.starttls.enable", "true")
        put("mail.smtp.host", host)
        put("mail.smtp.port", port)
    }

    // session
    val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(email.senderEmail, email.password)
        }
    })

    try {
        // create a message first
        val message = MimeMessage(session)
        message.setFrom(email.senderEmail)
        message.setRecipients(Message.RecipientType.TO, email.recipient)
        message.setSubject(email.subject)

        // create multipart
        val multipart = MimeMultipart()

        // create body text part
        val messageBodyPart = MimeBodyPart()
        messageBodyPart.setText(email.text)
        multipart.addBodyPart(messageBodyPart)

        // create file part
        // val fileBodyPart = MimeBodyPart()
        if (email.fileURLs.isNotEmpty()) {
            for (i in email.fileURLs.indices) {

                val fileURL = email.fileURLs[i]
                val fileName = email.fileNames[i]
                val file = File.createTempFile("temp_", fileName)

                // download
                URL(fileURL).openStream().use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }

                // attach
                val fileBodyPart = MimeBodyPart().apply {
                    dataHandler = DataHandler(FileDataSource(file))
                    this.fileName = fileName
                }
                multipart.addBodyPart(fileBodyPart)

            }
        }

        message.setContent(multipart)
        Transport.send(message)

    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun main() {
    val email = email(
        senderEmail = "cs346test@gmail.com",
        password = "qirk dyef rvbv bkka",
        recipient = "irishuang1105@gmail.com",
        subject = "Test Email",
        text = "This is a test email",
        fileURLs = listOf("https://gwnlngyvkxdpodenpyyj.supabase.co/storage/v1/object/sign/testEmailSending/CS341%20lec%2013.pdf?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJ0ZXN0RW1haWxTZW5kaW5nL0NTMzQxIGxlYyAxMy5wZGYiLCJpYXQiOjE3MzAzNTczNjUsImV4cCI6MTc2MTg5MzM2NX0.g03s4yqS5LP5EAtAPdNbsiPEBjsfvTs86M97vFj9XcE&t=2024-10-31T06%3A49%3A24.915Z"),
        fileNames = listOf("CS341 lec 13.pdf")
    )
    sendEmail(email)
}