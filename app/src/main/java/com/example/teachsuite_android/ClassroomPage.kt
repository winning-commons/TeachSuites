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

class ClassroomPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachSuite_androidTheme {
                ClassroomScreen()
            }
        }
    }
}

@Composable
fun ClassroomScreen() {
    var classrooms by remember { mutableStateOf(listOf<Classroom>()) }
    var isCreatingClassroom by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { isCreatingClassroom = true }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isCreatingClassroom) {
                CreateClassroomForm(
                    onCreateClassroom = { newClassroom ->
                        classrooms = classrooms + newClassroom
                        isCreatingClassroom = false
                    },
                    onCancel = { isCreatingClassroom = false }
                )
            } else {
                ClassroomList(classrooms = classrooms)
            }
        }
    }
}

@Composable
fun ClassroomList(classrooms: List<Classroom>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Classrooms",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (classrooms.isEmpty()) {
            Text(
                text = "No classrooms available.",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        } else {
            LazyColumn {
                items(classrooms) { classroom ->
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
fun CreateClassroomForm(
    onCreateClassroom: (Classroom) -> Unit,
    onCancel: () -> Unit
) {
    var teacherId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var googleClassId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create a Classroom",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = teacherId,
            onValueChange = { teacherId = it },
            label = { Text("Teacher ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Classroom Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Classroom Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = googleClassId,
            onValueChange = { googleClassId = it },
            label = { Text("Google Class ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(onClick = {
                if (teacherId.isNotBlank() && name.isNotBlank() && description.isNotBlank() && googleClassId.isNotBlank()) {
                    onCreateClassroom(Classroom(teacherId, name, description, googleClassId))
                }
            }) {
                Text("Create")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassroomScreenPreview() {
    TeachSuite_androidTheme {
        ClassroomScreen()
    }
}
