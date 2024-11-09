package com.example.teachsuite_android
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.com.example.teachsuite_android.ApiResponse
//import com.example.teachsuite_android.com.example.teachsuite_android.GradeExamsActivity
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme

class ClassroomDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the classroom data from intent
        val classroom = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("classroom", ApiResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra<ApiResponse>("classroom")
        }

        setContent {
            TeachSuite_androidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(classroom?.name ?: "Classroom") },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(Icons.Default.ArrowBack, "Back")
                                    }
                                }
                            )
                        }
                    ) { padding ->
                        if (classroom != null) {
                            ClassroomDetailContent(
                                classroom = classroom,
                                modifier = Modifier.padding(padding),
                                onGiveExamClick = { /* Handle give exam */ },
                                onGradeExamsClick = { val intent = Intent(this, GradeExamsActivity::class.java)
                                    startActivity(intent) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClassroomDetailContent(
    classroom: ApiResponse,
    modifier: Modifier = Modifier,
    onGiveExamClick: () -> Unit,
    onGradeExamsClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Classroom Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = classroom.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Description: ${classroom.description}")
                Text(text = "Google Class ID: ${classroom.googleClassroomId}")

                // Dummy data for now
                Text(text = "Students: 25")
                Text(text = "Active Exams: 2")
                Text(text = "Pending Grades: 15")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onGiveExamClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Assignment, "Give Exam")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Give Exam")
                }
            }

            Button(
                onClick = onGradeExamsClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Grade, "Grade Exams")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Grade Exams")
                }
            }
        }
    }
}