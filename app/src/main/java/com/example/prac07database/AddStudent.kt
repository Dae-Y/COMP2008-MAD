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
fun AddStudent(navController: NavHostController) {
    val db = DatabaseProvider.getDatabase(LocalContext.current)
    val studentDao = db.studentDao()

    var studentName by remember { mutableStateOf("") }
    var studentAge by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text("Student Name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = studentAge,
            onValueChange = { studentAge = it },
            label = { Text("Student Age") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Button(onClick = {
            if (studentName.isNotEmpty() && studentAge.isNotEmpty()) {
                studentDao.insertStudent(Student(name = studentName, age = studentAge.toInt()))
                navController.navigate("menu") // Navigate back to menu
            }
        }) {
            Text("Add Student")
        }
    }
}
