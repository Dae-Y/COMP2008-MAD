package com.example.prac07database

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun UpdateStudent(navController: NavHostController) {
    val db = DatabaseProvider.getDatabase(LocalContext.current)
    val studentDao = db.studentDao()

    var selectedStudentId by remember { mutableStateOf("") }
    var updatedName by remember { mutableStateOf("") }
    var updatedAge by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = selectedStudentId,
            onValueChange = { selectedStudentId = it },
            label = { Text("ID") }
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = updatedName,
                onValueChange = { updatedName = it },
                label = { Text("New Name") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )

            TextField(
                value = updatedAge,
                onValueChange = { updatedAge = it },
                label = { Text("New Age") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (selectedStudentId.isNotEmpty() && updatedName.isNotEmpty() && updatedAge.isNotEmpty()) {
                val id = selectedStudentId.toInt()
                studentDao.getAllStudents().find { it.id == id }?.let { student ->
                    studentDao.updateStudent(student.copy(name = updatedName, age = updatedAge.toInt()))
                    navController.navigate("menu")
                }
            }
        }) {
            Text("Update Student")
        }
    }
}
