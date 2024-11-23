package ca.uwaterloo.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import ca.uwaterloo.controller.DocumentController
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import ca.uwaterloo.view.UserSession

@Composable
fun DocumentUploadButton(text: String, documentType: String, documentController: DocumentController) {
    val coroutineScope = rememberCoroutineScope()
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var showFileDialog by remember { mutableStateOf(false) }

    Button(
        onClick = {
                showFileDialog = true
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(129.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF487896),
            contentColor = Color.White
        )
    ) {
        Text(text = text, color = Color.White, fontSize = 14.sp)
    }

    if (showFileDialog) {
        AwtWindow(
            create = {
                FileDialog(Frame(), "Select a file", FileDialog.LOAD).apply {
                    isVisible = true
                    selectedFile = file?.let { File(directory, it) }
                }
            },
            dispose = FileDialog::dispose
        )
        showFileDialog = false
    }

    LaunchedEffect(selectedFile) {
        selectedFile?.let { file ->
            coroutineScope.launch {
                val result = documentController.uploadDocument(
                    bucket = "user_documents",
                    userId = UserSession.userId ?: "DefaultUserId",
                    documentType = documentType,
                    documentName = file.name,
                    file = file
                )
                result.onSuccess {
                    println("Upload successful: $it")
                }.onFailure { error ->
                    println("Error uploading document: ${error.message}")
                }
            }
        }
    }
}