package com.example.teachsuite_android

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Class
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachsuite_android.ui.theme.TeachSuite_androidTheme

@Composable
fun TeacherDashboard(
    onClassroomsClick: () -> Unit = {},
    onExamBuilderClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Teacher Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        // Options Grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Classrooms Card
            DashboardCard(
                title = "Classrooms",
                icon = Icons.Default.Class,
                description = "Manage your classes and students",
                onClick = onClassroomsClick,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )

            // Exam Builder Card
            DashboardCard(
                title = "Exam Builder",
                icon = Icons.Default.Assignment,
                description = "Create and manage exams",
                onClick = onExamBuilderClick,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }

        // Quick Stats or Recent Activity could go here
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Quick Stats",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Active Classes: 0",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Total Students: 0",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Active Exams: 0",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Composable
fun DashboardCard(
    title: String,
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(250.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,  // This ensures horizontal centering
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center  // Add this
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center  // Make sure this is here
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeacherDashboardPreview() {
    TeachSuite_androidTheme {
        TeacherDashboard()
    }
}