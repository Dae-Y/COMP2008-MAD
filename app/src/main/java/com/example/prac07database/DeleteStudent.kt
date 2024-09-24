package com.example.prac07database

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DeleteStudent(navController: NavHostController) {
    val db = DatabaseProvider.getDatabase(LocalContext.current)
    val studentDao = db.studentDao()

    var studentIdToDelete by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = studentIdToDelete,
            onValueChange = { studentIdToDelete = it },
            label = { Text("ID") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (studentIdToDelete.isNotEmpty()) {
                val id = studentIdToDelete.toInt()
                studentDao.getAllStudents().find { it.id == id }?.let { studentToDelete ->
                    studentDao.deleteStudent(studentToDelete)
                    navController.navigate("menu")
                }
            }
        }) {
            Text("Delete Student")
        }
    }
}
