package ca.uwaterloo.persistence

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import java.io.File
import java.io.InputStream
import kotlin.time.Duration.Companion.minutes

class DocumentRepository(private val supabase: SupabaseClient) : IDocumentRepository {

    private val storage = supabase.storage

    override suspend fun uploadDocument(bucket: String, path: String, file: File): Result<String> {
        return try {
            if (!file.exists()) {
                return Result.failure(Exception("File does not exist: ${file.path}"))
            }
            val fileContent = file.readBytes()
            storage.from(bucket).upload(path, fileContent)
            Result.success("Document uploaded successfully.")
        } catch (e: Exception) {
            Result.failure(Exception("Error uploading document: ${e.message}"))
        }
    }

    override suspend fun downloadDocument(bucket: String, path: String): Result<ByteArray> {
        return try {
            val fileContent = storage.from(bucket).downloadAuthenticated(path)
            Result.success(fileContent)
        } catch (e: Exception) {
            Result.failure(Exception("Error downloading document: ${e.message}"))
        }
    }

    override suspend fun deleteDocument(bucket: String, path: String): Result<String> {
        return try {
            storage.from(bucket).delete(path)
            Result.success("Document deleted successfully.")
        } catch (e: Exception) {
            Result.failure(Exception("Error deleting document: ${e.message}"))
        }
    }

    override suspend fun createSignedUrl(bucket: String, path: String): Result<String> {
        return try {
            val url = storage.from(bucket).createSignedUrl(path, 5.minutes)
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(Exception("Error creating signed URL: ${e.message}"))
        }
    }

    override suspend fun listDocuments(bucket: String, path: String): Result<List<String>> {
        return try {
            val files = storage.from(bucket).list(path)
            val fileNames = files.map { it.name }
            Result.success(fileNames)
        } catch (e: Exception) {
            Result.failure(Exception("Error listing documents: ${e.message}"))
        }
    }

}
