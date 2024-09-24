package com.example.prac07database

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Menu(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Button(onClick = { navController.navigate("insert") }, modifier = Modifier.padding(8.dp)) {
            Text("Add Student")
        }

        Button(onClick = { navController.navigate("list") }, modifier = Modifier.padding(8.dp)) {
            Text("Show List")
        }

        Button(onClick = { navController.navigate("update") }, modifier = Modifier.padding(8.dp)) {
            Text("Update Student")
        }

        Button(onClick = { navController.navigate("delete") }, modifier = Modifier.padding(8.dp)) {
            Text("Delete Student")
        }
    }
}
