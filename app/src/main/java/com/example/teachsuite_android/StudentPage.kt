package com.example.teachsuite_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme

class StudentPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachSuite_androidTheme {
                StudentScreen(
                    classrooms = listOf(
                        Classroom("teacher1", "Math", "Basic Math Class", "1234"),
                        Classroom("teacher2", "Science", "Intro to Science", "5678")
                    )
                )
            }
        }
    }
}

@Composable
fun StudentScreen(classrooms: List<Classroom>) {
    var enrolledClasses by remember { mutableStateOf(listOf<Classroom>()) }
    var isAddingClass by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { isAddingClass = true }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isAddingClass) {
                AddClassForm(
                    classrooms = classrooms,
                    onAddClass = { newClass ->
                        if (!enrolledClasses.contains(newClass)) {
                            enrolledClasses = enrolledClasses + newClass
                        }
                        isAddingClass = false
                    },
                    onCancel = { isAddingClass = false }
                )
            } else {
                EnrolledClassesList(enrolledClasses = enrolledClasses)
            }
        }
    }
}

@Composable
fun EnrolledClassesList(enrolledClasses: List<Classroom>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Classes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (enrolledClasses.isEmpty()) {
            Text(
                text = "No classes joined.",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        } else {
            LazyColumn {
                items(enrolledClasses) { classroom ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Name: ${classroom.name}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(text = "Teacher ID: ${classroom.teacherId}")
                            Text(text = "Description: ${classroom.description}")
                            Text(text = "Google Class ID: ${classroom.googleClassId}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddClassForm(
    classrooms: List<Classroom>,
    onAddClass: (Classroom) -> Unit,
    onCancel: () -> Unit
) {
    var googleClassIdInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Join a Class",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = googleClassIdInput,
            onValueChange = { googleClassIdInput = it },
            label = { Text("Enter Google Classroom ID") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(onClick = {
                val matchedClass = classrooms.find { it.googleClassId == googleClassIdInput }
                if (matchedClass != null) {
                    onAddClass(matchedClass)
                    errorMessage = ""
                } else {
                    errorMessage = "Class not found. Please check the ID."
                }
            }) {
                Text("Join Class")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentScreenPreview() {
    TeachSuite_androidTheme {
        StudentScreen(
            classrooms = listOf(
                Classroom("teacher1", "Math", "Basic Math Class", "1234"),
                Classroom("teacher2", "Science", "Intro to Science", "5678")
            )
        )
    }
}
