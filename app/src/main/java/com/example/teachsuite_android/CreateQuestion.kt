package com.example.teachsuite_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme

class CreateQuestion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachSuite_androidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CreateQuestionScreen(
                        modifier = Modifier.padding(innerPadding),
                        onStartQuestionCreation = {
                            startActivity(Intent(this, QuestionCreationActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CreateQuestionScreen(
    modifier: Modifier = Modifier,
    onStartQuestionCreation: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section with title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
        ) {
            Text(
                text = "Create a Question",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Choose the type of question you want to create",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        // Add space between title and button
        Spacer(modifier = Modifier.height(100.dp)) // Adjust this value to position the button higher or lower

        // Button positioned closer to the top
        Button(
            onClick = onStartQuestionCreation,
            modifier = Modifier
                .fillMaxWidth(0.8f) // Adjust button width to 80% of the screen
                .height(50.dp), // Fixed height for the button
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Start Creating Questions")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateQuestionScreenPreview() {
    TeachSuite_androidTheme {
        CreateQuestionScreen(onStartQuestionCreation = {})
    }
}
