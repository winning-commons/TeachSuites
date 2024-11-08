package com.example.teachsuite_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme

class QuestionCreationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachSuite_androidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        QuestionCreationScreen(modifier = Modifier.padding(innerPadding))
                    }
                )
            }
        }
    }
}

@Composable
fun QuestionCreationScreen(modifier: Modifier = Modifier) {
    var selectedQuestionType by remember { mutableStateOf(QuestionType.MULTIPLE_CHOICE) }
    var questionsList by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Toggle between Multiple Choice and Short Response
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { selectedQuestionType = QuestionType.MULTIPLE_CHOICE },
                colors = if (selectedQuestionType == QuestionType.MULTIPLE_CHOICE)
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                else
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Multiple Choice")
            }

            Button(
                onClick = { selectedQuestionType = QuestionType.SHORT_RESPONSE },
                colors = if (selectedQuestionType == QuestionType.SHORT_RESPONSE)
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                else
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Short Response")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Render the selected question type screen
        if (selectedQuestionType == QuestionType.MULTIPLE_CHOICE) {
            MultipleChoiceQuestionScreen(
                onSaveQuestion = { questionsList = questionsList + it }
            )
        } else {
            ShortResponseQuestionScreen(
                onSaveQuestion = { questionsList = questionsList + it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display saved questions
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(questionsList.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = questionsList[index],
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

// Composable for Multiple Choice Question Creation
@Composable
fun MultipleChoiceQuestionScreen(onSaveQuestion: (String) -> Unit) {
    var questionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(listOf("")) }
    var correctAnswerIndex by remember { mutableStateOf(-1) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Create a Multiple Choice Question",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Enter your question") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(options.size) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = correctAnswerIndex == index,
                        onClick = { correctAnswerIndex = index }
                    )
                    TextField(
                        value = options[index],
                        onValueChange = { text ->
                            options = options.toMutableList().apply { set(index, text) }
                        },
                        label = { Text("Option ${index + 1}") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        options = options.toMutableList().apply { removeAt(index) }
                        if (correctAnswerIndex == index) correctAnswerIndex = -1
                        if (correctAnswerIndex > index) correctAnswerIndex -= 1
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove Option")
                    }
                }
            }
        }

        Button(onClick = { options = options + "" }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Option")
        }

        Button(
            onClick = {
                if (questionText.isNotBlank() && options.all { it.isNotBlank() } && correctAnswerIndex != -1) {
                    val newQuestion = """
                        Question: $questionText
                        Options:
                        ${options.mapIndexed { i, opt -> "${i + 1}. $opt" }.joinToString("\n")}
                        Correct Answer: ${options[correctAnswerIndex]}
                    """.trimIndent()
                    onSaveQuestion(newQuestion)
                    questionText = ""
                    options = listOf("")
                    correctAnswerIndex = -1
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Multiple Choice Question")
        }
    }
}

// Composable for Short Response Question Creation
@Composable
fun ShortResponseQuestionScreen(onSaveQuestion: (String) -> Unit) {
    var questionText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Create a Short Response Question",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Enter your question") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (questionText.isNotBlank()) {
                    val newQuestion = "Short Response Question: $questionText"
                    onSaveQuestion(newQuestion)
                    questionText = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Short Response Question")
        }
    }
}

enum class QuestionType {
    MULTIPLE_CHOICE,
    SHORT_RESPONSE
}

@Preview(showBackground = true)
@Composable
fun QuestionCreationPreview() {
    TeachSuite_androidTheme {
        QuestionCreationScreen()
    }
}
