package com.example.teachsuite_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ShortResponseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShortResponseScreen()
        }
    }
}

@Composable
fun ShortResponseScreen() {
    Text(text = "Short Response Question Creator")
}
