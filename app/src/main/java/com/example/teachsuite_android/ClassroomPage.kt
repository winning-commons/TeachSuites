// ClassroomPage.kt
package com.example.teachsuite_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.teachsuite_android.com.example.teachsuite_android.ApiResponse
import com.example.teachsuite_android.com.example.teachsuite_android.fetchClassrooms
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme
import kotlinx.coroutines.launch

class ClassroomPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // State to hold the fetched classrooms list
        val classroomsState = mutableStateOf<List<ApiResponse>>(emptyList())

        // Launch a coroutine to fetch classrooms when the page loads
        lifecycleScope.launch {
            val classrooms = fetchClassrooms() // Calls the provided fetchClassrooms function
            classroomsState.value = classrooms
        }

        setContent {
            TeachSuite_androidTheme {
                ClassroomScreen(classrooms = classroomsState.value)
            }
        }
    }
}

@Composable
fun ClassroomScreen(classrooms: List<ApiResponse>) {
    var isCreatingClassroom by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                        // Handle adding new classroom if necessary
                        isCreatingClassroom = false
                    },
                    onCancel = { isCreatingClassroom = false }
                )
            } else {
                ClassroomList(
                    classrooms = classrooms,
                    onClassroomClick = { classroom ->
                        val intent = Intent(context, ClassroomDetailActivity::class.java)
                        intent.putExtra("classroom", classroom)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun ClassroomList(
    classrooms: List<ApiResponse>,
    onClassroomClick: (ApiResponse) -> Unit = {}
) {
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
                            .padding(vertical = 8.dp)
                            .clickable { onClassroomClick(classroom) },
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
                            Text(text = "Google Class ID: ${classroom.googleClassroomId}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateClassroomForm(
    onCreateClassroom: (ApiResponse) -> Unit,
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
                    val newClassroom = ApiResponse(
                        id = "Generated ID",
                        teacherId = teacherId,
                        name = name,
                        googleClassroomId = googleClassId,
                        description = description,
                        isActive = true,
                        createdAt = "Current Date",
                        updatedAt = "Current Date"
                    )
                    onCreateClassroom(newClassroom)
                }
            }) {
                Text("Create")
            }
        }
    }
}
