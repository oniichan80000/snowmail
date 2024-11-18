package controller

import ca.uwaterloo.controller.DocumentController
import ca.uwaterloo.persistence.IDocumentRepository
import ca.uwaterloo.persistence.IJobApplicationRepository
import integration.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import model.email
import persistence.JobApplicationRepository
import service.sendEmail


class SendEmailController(private val jobApplicationRepository: IJobApplicationRepository,
                          private val documentRepository: IDocumentRepository) {
    suspend fun send_email(
        senderEmail: String,
        password: String,
        recipient: String,
        subject: String,
        text: String,
        // Modified Parameters from Sprint 3
        buckets: List<String>,
        documentsType: List<String>,
        documentsName: List<String>,
        // ---------
        userID: String,
        jobTitle: String,
        companyName: String

    ) {
        val fileURLs = mutableListOf<String>()
        for (i in documentsType.indices) {
            val bucket = buckets[i]
            val documentType = documentsType[i]
            val documentName = documentsName[i]
            val documentController = DocumentController(documentRepository)
            val signedUrl = documentController.viewDocument(bucket, userID, documentType, documentName).getOrNull()!!
            fileURLs.add(signedUrl)
        }
        val Email = email(senderEmail, password, recipient, subject, text, fileURLs, documentsName)
        sendEmail(Email)

        // update last refresh time if necessary
        jobApplicationRepository.updateRefreshTime(userID)

        // save job application
        jobApplicationRepository.createJobApplication(
            userID,
            jobTitle,
            companyName,
            recipient
        )

    }
}

// functions to send email



suspend fun main() {
    val senderEmail = "cs346test@gmail.com"
    val password = "qirk dyef rvbv bkka"
    val recipient = "irishuang1105@gmail.com"
    val subject = "subject"
    val text = "text"
    val userID = "ed52b6c4-2ae8-4b58-bacd-adc00082a505"
    val jobTitle = "Software Developer"
    val companyName = "Google"

    val documentController = DocumentController(SupabaseClient().documentRepository)
    val bucket = "user_documents"
    val documentType = "Resume"
    val documentName = "STAT231_Tutorial_4.pdf"

    val supabase = createSupabaseClient(
        supabaseUrl = "https://gwnlngyvkxdpodenpyyj.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd3bmxuZ3l2a3hkcG9kZW5weXlqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjc5MTAxNTEsImV4cCI6MjA0MzQ4NjE1MX0.olncAUMxSOjcr0YjssWXThtXDXC3q4zasdNYdwavt8g"
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }
    val JobApplicationRepository = JobApplicationRepository(supabase)
    val c = SendEmailController(JobApplicationRepository, SupabaseClient().documentRepository)
    c.send_email(senderEmail, password, recipient, subject, text, listOf(bucket), listOf(documentType), listOf(documentName), userID, jobTitle, companyName)
}









