package com.example.teachsuite_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.teachsuite_android.com.example.teachsuite_android.GradeRequest
import com.example.teachsuite_android.com.example.teachsuite_android.GradeResponse
import com.example.teachsuite_android.com.example.teachsuite_android.gradeSARequest
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme
import kotlinx.coroutines.launch

class GradeExamsActivity : ComponentActivity() {
    private var gradeResult by mutableStateOf<GradeResponse?>(null)  // Use GradeResponse directly

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachSuite_androidTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Grade Exams") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                }
                            }
                        )
                    }
                ) { padding ->
                    GradeExamsScreen(
                        modifier = Modifier.padding(padding),
                        onGradeWithAiClick = { gradeWithAI() },
                        gradeResult = gradeResult  // Pass the GradeResponse result to UI
                    )
                }
            }
        }
    }

    private fun gradeWithAI() {
        val gradeRequest = GradeRequest(
            saInput = "the sun",  // Hardcoded value
            saQuestion = "Which planet is known as the Red Planet?"    // Hardcoded value
        )

        lifecycleScope.launch {
            gradeResult = gradeSARequest(gradeRequest)  // Call the separated function
            if (gradeResult != null) {
                Log.i("GradeExamsActivity", "Grading response: $gradeResult")
            } else {
                Log.e("GradeExamsActivity", "Failed to retrieve grading result.")
            }
        }
    }
}
@Composable
fun GradeExamsScreen(
    modifier: Modifier = Modifier,
    onGradeWithAiClick: () -> Unit,  // Ensure this parameter is included
    gradeResult: GradeResponse?  // Use GradeResponse type
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hardcoded answers card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Student Answers",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Which planet is known as the Red Planet?: the sun.")
                Text(text = "Question 2: answer to question 2...")
                Text(text = "Question 3: answer to question 3...")
            }
        }

        // "Grade with AI" button
        Button(
            onClick = onGradeWithAiClick,  // Ensure this is passed correctly
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Grade with AI")
        }

        // Display grade result if available
        gradeResult?.let {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Grade Result:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Points: ${it.points}")
            Text("Feedback: ${it.feedback}")
            Text("Improvements: ${it.improvements}")
            Text("Sources: ${it.sources}")
        }
    }
}
