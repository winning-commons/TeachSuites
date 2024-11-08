package com.example.teachsuite_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeachSuite_androidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        onExamBuilderClick = {
                            context.startActivity(Intent(context, CreateQuestion::class.java))
                        },
                        onClassroomsClick = {
                            context.startActivity(Intent(context, ClassroomPage::class.java))
                        }
                    )
                }
            }
        }
    }
}


enum class Screen {
    START,
    ROLE_SELECTION,
    TEACHER_DASHBOARD
}

enum class UserRole {
    TEACHER,
    STUDENT
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onExamBuilderClick: () -> Unit,
    onClassroomsClick: () -> Unit
) {
    var currentScreen by remember { mutableStateOf(Screen.START) }
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }

    when (currentScreen) {
        Screen.START -> StartPage(
            onGetStartedClick = { currentScreen = Screen.ROLE_SELECTION }
        )
        Screen.ROLE_SELECTION -> RoleSelectionPage(
            onRoleSelected = { role ->
                selectedRole = role
                if (role == UserRole.TEACHER) {
                    currentScreen = Screen.TEACHER_DASHBOARD
                }
            }
        )
        Screen.TEACHER_DASHBOARD -> TeacherDashboard(
            onExamBuilderClick = onExamBuilderClick,
            onClassroomsClick = onClassroomsClick  // Pass through the classrooms click handler
        )
    }
}
@Composable
fun StartPage(onGetStartedClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top section with logo and welcome text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = "TeachSuites Logo",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to TeachSuites",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your complete teaching companion",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        // Middle section with features
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            FeatureItem(
                title = "Create Exams",
                description = "Build and manage your assessments with ease"
            )
            FeatureItem(
                title = "Track Progress",
                description = "Monitor student performance in real-time"
            )
            FeatureItem(
                title = "Google Classroom",
                description = "Seamless integration with Google Classroom"
            )
        }

        // Bottom section with buttons
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Get Started")
            }
        }
    }
}

@Composable
fun RoleSelectionPage(onRoleSelected: (UserRole) -> Unit, modifier: Modifier = Modifier) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "I am a...",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoleOption(
                role = UserRole.TEACHER,
                icon = Icons.Default.School,
                isSelected = selectedRole == UserRole.TEACHER,
                onSelect = { selectedRole = UserRole.TEACHER }
            )

            RoleOption(
                role = UserRole.STUDENT,
                icon = Icons.Default.Person,
                isSelected = selectedRole == UserRole.STUDENT,
                onSelect = { selectedRole = UserRole.STUDENT }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { selectedRole?.let(onRoleSelected) },
            enabled = selectedRole != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun RoleOption(
    role: UserRole,
    icon: ImageVector,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(140.dp)
            .clickable(onClick = onSelect)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = role.name,
                modifier = Modifier.size(48.dp),
                tint = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = role.name.lowercase().capitalize(),
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun FeatureItem(title: String, description: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StartPagePreview() {
    TeachSuite_androidTheme {
        StartPage(onGetStartedClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun RoleSelectionPreview() {
    TeachSuite_androidTheme {
        RoleSelectionPage(onRoleSelected = {})
    }
}