package com.example.prac07database

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun StudentList(navController: NavHostController) {
    val db = DatabaseProvider.getDatabase(LocalContext.current)
    val studentDao = db.studentDao()

    var students by remember { mutableStateOf(emptyList<Student>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Button to show all students
        Button(onClick = {
            students = studentDao.getAllStudents()
        }) {
            Text("Show All Students")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Scrollable list of students
        LazyColumn(
            modifier = Modifier
                .weight(1f)  // This makes the LazyColumn take all the remaining space
                .fillMaxWidth()
        ) {
            items(students) { student ->
                SingleStudent(student)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Back to Menu button, which is fixed at the bottom
        Button(onClick = { navController.navigate("menu") }) {
            Text("Back to Menu")
        }
    }
}

@Composable
fun SingleStudent(student: Student) {
    val context = LocalContext.current
    val imageId = R.drawable.studentimg
    // Use a placeholder image if student.imageId is null
    Card(
        onClick = {
            Toast.makeText(context, "${student.id} is clicked", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "student photo",
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Student ID: ${student.id}")
                Text(text = "Student Name: ${student.name}")
                Text(text = "Student Age: ${student.age}")
            }
        }
    }
}
