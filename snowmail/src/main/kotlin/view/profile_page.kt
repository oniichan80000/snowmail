package ca.uwaterloo.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun ProfilePage() {
    var email by remember { mutableStateOf("-------@gmail.com") }
    var location by remember { mutableStateOf("-------") }
    var phone by remember { mutableStateOf("-------") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E2E2)),
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Name
            Text(
                "Name",
                fontSize = 24.sp,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        // Outer Card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp),
            elevation = 8.dp
        ) {

            Column(
               modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Edit button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF487896),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Edit",
                            style = TextStyle(fontSize = 10.sp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Contact Information
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Contact Info
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            ProfileDetail(label = "Email Address", value = email)
                            ProfileDetail(label = "Location", value = location)
                            ProfileDetail(label = "Phone", value = phone)
                        }

                        // Resume Link
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Resume.pdf",
                                modifier = Modifier.padding(end = 32.dp),
                                style = MaterialTheme.typography.body1.copy(
                                    fontSize = 14.sp,
                                    color = Color(0xFF2669A0)
                                )
                            )

                        }
                    }
                }

                SectionTitle("Education")
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(" ")
                    }
                }

                SectionTitle("Work experience")
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp).height(80.dp)) {

                }

                SectionTitle("Skills")
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp).height(80.dp)) {

                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(fontSize = 14.sp, color = Color.Gray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 16.dp, bottom = 8.dp),
        textAlign = TextAlign.Start
    )
}

@Composable
fun ProfileDetail(label: String, value: String) {
    Row {
        Text(
            "$label: ",
            style = TextStyle(fontSize = 14.sp, color = Color.Black)
        )
        Text(
            value,
            style = TextStyle(fontSize = 14.sp, color = Color.Black)
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Profile Page") {
        ProfilePage()
    }
}
