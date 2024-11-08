package com.example.teachsuite_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MultipleChoiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultipleChoiceScreen()
        }
    }
}

@Composable
fun MultipleChoiceScreen() {
    Text(text = "Multiple Choice Question Creator")
}
