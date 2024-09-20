package com.example.prac06composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// to use images instead of texts for calculator buttons
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.prac06composecalculator.ui.theme.Prac06ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prac06ComposeCalculatorTheme {
                val dataViewModel:DataViewModel = DataViewModel()
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan),
                    contentAlignment = Alignment.Center){
                    mainScreen(viewModel = dataViewModel)
                    // inflate the the views inside a parent box
                }
            }
        }
    }
}

// Row of two inputs
@Composable
fun inputTaking(viewModel: DataViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        TextField(
            value = viewModel.firstNumber,
            onValueChange = { text ->
                viewModel.firstNumber = text
                viewModel.showResult = false // Hide result when user types
            },
            label = { Text("First number") },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        TextField(
            value = viewModel.secondNumber,
            onValueChange = { text ->
                viewModel.secondNumber = text
                viewModel.showResult = false // Hide result when user types
            },
            label = { Text("Second number") },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }
}
@Preview
@Composable
fun inputTakingView(){
    val dataViewModel:DataViewModel = DataViewModel()
    inputTaking(viewModel = dataViewModel)
}

// Buttons for calculators, I'm using Built-in Icons from Icons
// https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons
// Also custom images from the internet could be used.
// place the images inside res/drawable directory in the project
// and load them using painterResource, e.g.) if I have add_icon.png
@Composable
fun calculatorButtons(viewModel: DataViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ){
        // Add button
        Button(onClick = { viewModel.add() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            // Text(text = "Add")
            // Image(painter = painterResource(id = R.drawable.add_icon), contentDescription = "Add")
        }
        Spacer(modifier = Modifier.padding(8.dp))

        // Subtract button
        Button(onClick = { viewModel.subtract() }) {
            Image(painter = painterResource(id = R.drawable.minus), contentDescription = "Subtract")
        }
        Spacer(modifier = Modifier.padding(8.dp))

        // Multiply button
        Button(onClick = { viewModel.multiply() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Multiply")
        }
        Spacer(modifier = Modifier.padding(8.dp))

        // Divide button
        Button(onClick = { viewModel.divide() }) {
            Image(painter = painterResource(id = R.drawable.divide), contentDescription = "Divide")
        }
    }
}
@Preview
@Composable
fun calculatorButtonsView(){
    val dataViewModel:DataViewModel = DataViewModel()
    calculatorButtons(viewModel = dataViewModel)
}

// 'Result' view
@Composable
fun showResult(viewModel: DataViewModel) {
    // Only display the result when the showResult flag is true.
    if (viewModel.showResult) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Result: ${viewModel.result}",
                style = TextStyle(fontSize = 18.sp)
            )
        }
    }
}
@Preview
@Composable
fun showResultView(){
    val dataViewModel:DataViewModel = DataViewModel()
    showResult(viewModel = dataViewModel)
}

// Put all the views inside a column
@Composable
fun mainScreen(viewModel: DataViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center
    ){
        inputTaking(viewModel = viewModel)
        calculatorButtons(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        showResult(viewModel = viewModel) // Show result only when necessary
    }
}
@Preview
@Composable
fun mainScreenView(){
    val dataViewModel:DataViewModel = DataViewModel()
    mainScreen(viewModel = dataViewModel)
}
