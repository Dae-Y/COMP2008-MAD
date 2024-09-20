package com.example.composecalculator

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
import androidx.lifecycle.ViewModel
import com.example.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCalculatorTheme {
                val dataViewModel:DataViewModel = DataViewModel()
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan),
                    contentAlignment = Alignment.Center){
                    mainScreen(viewModel = dataViewModel)
                }
            }
        }
    }
}

@Composable
fun inputTaking(viewModel: DataViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        TextField(
            value = viewModel.firstNumber,
            onValueChange = {text->viewModel.firstNumber =text},
            label = {Text("First number")},
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        TextField(
            value = viewModel.secondNumber,
            onValueChange = {text->viewModel.secondNumber =text},
            label = {Text("Second number")},
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

@Composable
fun calculatorButtons(viewModel: DataViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ){
        Button(onClick = { viewModel.add() }) {
            Text(text = "Add")
        }
    }
}
@Preview
@Composable
fun calculatorButtonsView(){
    val dataViewModel:DataViewModel = DataViewModel()
    calculatorButtons(viewModel = dataViewModel)
}

@Composable
fun showResult(viewModel: DataViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "The result is ${viewModel.result}",
            style = TextStyle(fontSize = 18.sp)
        )
    }
}

@Preview
@Composable
fun showResultView(){
    val dataViewModel:DataViewModel = DataViewModel()
    showResult(viewModel = dataViewModel)
}

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
        showResult(viewModel = viewModel)
    }
}
@Preview
@Composable
fun mainScreenView(){
    val dataViewModel:DataViewModel = DataViewModel()
    mainScreen(viewModel = dataViewModel)
}

