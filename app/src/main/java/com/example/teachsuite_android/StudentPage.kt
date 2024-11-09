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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.com.example.teachsuite_android.fetchStudentClasses
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme
import kotlinx.coroutines.launch

class StudentPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachSuite_androidTheme {
                val studentId = "b1f19de4-8ba4-45b8-b748-cd0e6509a44a"
                var classrooms by remember { mutableStateOf<List<Classroom>>(emptyList()) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        classrooms = fetchStudentClasses(studentId)
                    }
                }

                StudentScreen(classrooms = classrooms)
            }
        }
    }
}

@Composable
fun StudentScreen(classrooms: List<Classroom>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add any additional actions here if needed */ }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            EnrolledClassesList(enrolledClasses = classrooms)
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
