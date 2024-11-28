//package ca.uwaterloo.view.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.graphics.Color
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.ui.window.Dialog
//import kotlinx.coroutines.launch
//import java.awt.FileDialog
//import java.awt.Frame
//import java.io.File
//
//import ca.uwaterloo.controller.DocumentController
//import ca.uwaterloo.view.UserSession
//
//@Composable
//fun DocumentUploadButton2(documentController: DocumentController) {
//    val coroutineScope = rememberCoroutineScope()
//    var selectedFile by remember { mutableStateOf<File?>(null) }
//    var showFileDialog by remember { mutableStateOf(false) }
//    var showDropdownMenu by remember { mutableStateOf(false) }
//    var selectedDocumentType by remember { mutableStateOf<String?>(null) }
//    val documentTypes = listOf("Resume", "Cover Letter", "Transcript", "Certificates", "Others")
//
//    Column {
//        Button(
//            onClick = { showDropdownMenu = true },
//            shape = RoundedCornerShape(5.dp),
//            modifier = Modifier
//                .width(130.dp)
//                .height(40.dp),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color(0xFF487896),
//                contentColor = Color.White
//            )
//        ) {
//            Text(text = "Upload", color = Color.White, fontSize = 14.sp)
//        }
//
//        DropdownMenu(
//            expanded = showDropdownMenu,
//            onDismissRequest = { showDropdownMenu = false }
//        ) {
//            documentTypes.forEach { documentType ->
//                DropdownMenuItem(onClick = {
//                    selectedDocumentType = documentType
//                    showDropdownMenu = false
//                    showFileDialog = true
//                }) {
//                    Text(text = documentType)
//                }
//            }
//        }
//
//        if (showFileDialog) {
//            FilePickerDialog(
//                onFileSelected = { file ->
//                    selectedFile = file
//                    showFileDialog = false
//
//                    // Trigger upload when file is selected
//                    file?.let { selectedFile ->
//                        selectedDocumentType?.let { documentType ->
//                            uploadDocument(
//                                documentController,
//                                documentType,
//                                selectedFile,
//                                coroutineScope
//                            )
//                        }
//                    }
//                },
//                onDismiss = {
//                    showFileDialog = false
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun FilePickerDialog(
//    onFileSelected: (File?) -> Unit,
//    onDismiss: () -> Unit
//) {
//    Dialog(onDismissRequest = onDismiss) {
//        val fileDialog = remember {
//            FileDialog(Frame(), "Select Document", FileDialog.LOAD).apply {
//                isVisible = true
//            }
//        }
//
//        DisposableEffect(fileDialog) {
//            val selectedFile = fileDialog.file?.let { File(fileDialog.directory, it) }
//            onFileSelected(selectedFile)
//            fileDialog.dispose()
//            onDismiss()
//
//            onDispose {
//                fileDialog.dispose()
//            }
//        }
//    }
//}
//
//private fun uploadDocument(
//    documentController: DocumentController,
//    documentType: String,
//    file: File,
//    coroutineScope: CoroutineScope
//) {
//    coroutineScope.launch {
//        val result = documentController.uploadDocument(
//            bucket = "user_documents",
//            userId = UserSession.userId ?: "DefaultUserId",
//            documentType = documentType,
//            documentName = file.name,
//            file = file
//        )
//
//        result.onSuccess {
//            println("Upload successful: $it")
//        }.onFailure { error ->
//            handleUploadError(documentController, documentType, file, error)
//        }
//    }
//}
//
//fun handleUploadError(
//    documentController: DocumentController,
//    documentType: String,
//    file: File,
//    error: Throwable
//) {
//    println("Error uploading document: ${error.message}")
//
//    // Check if the file already exists
//    if (error.message?.contains("already exists") == true) {
//        val userId = UserSession.userId ?: "DefaultUserId"
//
//        // Attempt to delete existing document
//        val deleteResult = documentController.deleteDocument(
//            bucket = "user_documents",
//            userId = userId,
//            documentType = documentType,
//            documentName = file.name
//        )
//
//        deleteResult.onSuccess {
//            println("Existing document deleted: $it")
//
//            // Re-upload the document
//            val reuploadResult = documentController.uploadDocument(
//                bucket = "user_documents",
//                userId = userId,
//                documentType = documentType,
//                documentName = file.name,
//                file = file
//            )
//
//            reuploadResult.onSuccess {
//                println("Re-upload successful: $it")
//            }.onFailure { reuploadError ->
//                println("Error re-uploading document: ${reuploadError.message}")
//            }
//        }.onFailure { deleteError ->
//            println("Error deleting existing document: ${deleteError.message}")
//        }
//    }
//}