package com.example.teachsuite_android

import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        onShortResponseClick = {
                            startActivity(Intent(this, ShortResponseActivity::class.java))
                        },
                        onMultipleChoiceClick = {
                            startActivity(Intent(this, MultipleChoiceActivity::class.java))
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
    onShortResponseClick: () -> Unit,
    onMultipleChoiceClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top section with title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 48.dp)
        ) {
            Text(
                text = "Create a Question",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Choose the type of question you want to create",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        // Middle section with options
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            Button(
                onClick = onShortResponseClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Create Short Response")
            }

            OutlinedButton(
                onClick = onMultipleChoiceClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Create Multiple Choice")
            }
        }

        // Bottom section with a note or additional info
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = "Start building engaging questions now!",
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateQuestionScreenPreview() {
    TeachSuite_androidTheme {
        CreateQuestionScreen(
            onShortResponseClick = {},
            onMultipleChoiceClick = {}
        )
    }
}
