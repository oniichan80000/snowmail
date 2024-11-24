package service

import ca.uwaterloo.persistence.DocumentRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import java.util.*
import javax.mail.*

// constants
val this_host = "imap.gmail.com"
val this_port = 993

data class email (
    val senderEmail: String,
    val subject: String,
    val text: String,
    val fileNames: List<String>,
    val attachLink: List<String>
)

suspend fun searchEmails(userAccount: String, userPassword: String,
                 last_refresh_time: Date,
                 recruiterEmails: List<String>, documentRepository: DocumentRepository): List<email> {

    val properties = Properties().apply {
        put("mail.imap.host", this_host)
        put("mail.imap.port", this_port)
        put("mail.imap.ssl.enable", "true")
    }

    val session: Session = Session.getInstance(properties)
    var result = mutableListOf<email>()

    try {
        val store: Store = session.getStore("imap")
        store.connect(userAccount, userPassword)

        val inbox: Folder = store.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)

        val messages: Array<Message> = inbox.messages

        for (i in messages.size - 1 downTo 0) {

            val message = messages[i]

            // only search to last refresh time
            if (message.receivedDate.before(last_refresh_time)) {
                break
            }

            // only return those recruiters' emails
            val emailAddress = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}").find(message.from[0].toString())?.value
            if (recruiterEmails.contains(emailAddress!!)) {
                val content = message.content
                var text = ""
                val attachmentLinks = mutableListOf<String>()
                val fileNames = mutableListOf<String>()


                if (content is String) {
                    text = content
                } else if (content is Multipart) {
                    for (j in 0 until content.count) {
                        val bodyPart = content.getBodyPart(j)
                        if (bodyPart.isMimeType("text/plain")) {
                            text += bodyPart.content
                        } else if (Part.ATTACHMENT.equals(bodyPart.disposition, ignoreCase = true)) {
                            val attachmentStream = bodyPart.inputStream ?: continue
                            var fileName = bodyPart.fileName ?: "unknown"
                            fileName = fileName.replace("\\s".toRegex(), "")
                            println(fileName)
                            val url = documentRepository.uploadEmailAttachment(fileName, attachmentStream).getOrNull()!!
                            attachmentLinks.add(url)
                            fileNames.add(fileName)
                        }
                    }
                }
                var subject: String
                if (message.subject == null) {
                    subject = ""
                } else {
                    subject = message.subject
                }
                val item = email(emailAddress, subject, text, fileNames, attachmentLinks)
                println(item)
                result.add(item)
                print("HERE2")
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}




fun createSpecificDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1) // Month is 0-based, so subtract 1
    calendar.set(Calendar.DAY_OF_MONTH, day)
    calendar.set(Calendar.HOUR_OF_DAY, hour) // 24-hour format
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, second)
    calendar.set(Calendar.MILLISECOND, 0) // Optional: Set milliseconds to 0
    return calendar.time
}






suspend fun main() {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://gwnlngyvkxdpodenpyyj.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd3bmxuZ3l2a3hkcG9kZW5weXlqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjc5MTAxNTEsImV4cCI6MjA0MzQ4NjE1MX0.olncAUMxSOjcr0YjssWXThtXDXC3q4zasdNYdwavt8g"
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }
    val documentRepository = DocumentRepository(supabase)
    val result = searchEmails("cs346test@gmail.com", "qirk dyef rvbv bkka", createSpecificDateTime(2021, 9, 1, 0, 0, 0), listOf("irishuang1105@gmail.com"), documentRepository)
}
