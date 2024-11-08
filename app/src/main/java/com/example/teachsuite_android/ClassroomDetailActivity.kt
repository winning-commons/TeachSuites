package com.example.teachsuite_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme

class ClassroomDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get classroom data from intent
        val classroom = intent?.getParcelableExtra<Classroom>("classroom")

        setContent {
            TeachSuite_androidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    classroom?.let {
                        ClassroomDetailContent(it, onBackClick = { finish() })
                    }
                }
            }
        }
    }
}

@Composable
fun ClassroomDetailContent(
    classroom: Classroom,
    onBackClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Students", "Exams", "Submissions")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(classroom.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            // Content based on selected tab
            when (selectedTab) {
                0 -> StudentsList(classroom)
                1 -> ExamsList(classroom)
                2 -> SubmissionsList(classroom)
            }
        }
    }
}

@Composable
fun StudentsList(classroom: Classroom) {
    var students by remember { mutableStateOf(listOf<Student>()) } // Replace with actual data

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Students",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = { /* Handle add student */ }) {
                Icon(Icons.Default.Add, "Add student")
                Spacer(Modifier.width(4.dp))
                Text("Add Student")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(students) { student ->
                StudentCard(student)
            }
        }
    }
}

@Composable
fun ExamsList(classroom: Classroom) {
    var exams by remember { mutableStateOf(listOf<Exam>()) } // Replace with actual data

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Exams",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = { /* Handle assign exam */ }) {
                Icon(Icons.Default.Add, "Assign exam")
                Spacer(Modifier.width(4.dp))
                Text("Assign Exam")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exams) { exam ->
                ExamCard(exam)
            }
        }
    }
}

@Composable
fun SubmissionsList(classroom: Classroom) {
    var submissions by remember { mutableStateOf(listOf<Submission>()) } // Replace with actual data

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Submissions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(submissions) { submission ->
                SubmissionCard(submission)
            }
        }
    }
}

data class Student(
    val id: String,
    val name: String,
    val email: String
)

data class Exam(
    val id: String,
    val title: String,
    val dueDate: String,
    val status: String
)

data class Submission(
    val studentId: String,
    val examId: String,
    val status: String,
    val grade: Float?
)

@Composable
fun StudentCard(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(student.name, fontWeight = FontWeight.Bold)
            Text(student.email)
        }
    }
}

@Composable
fun ExamCard(exam: Exam) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(exam.title, fontWeight = FontWeight.Bold)
            Text("Due: ${exam.dueDate}")
            Text("Status: ${exam.status}")
        }
    }
}

@Composable
fun SubmissionCard(submission: Submission) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Student ID: ${submission.studentId}", fontWeight = FontWeight.Bold)
            Text("Status: ${submission.status}")
            submission.grade?.let {
                Text("Grade: $it")
            }
        }
    }
}