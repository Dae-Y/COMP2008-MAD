package com.example.composecalculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class DataViewModel:ViewModel(){
    var firstNumber by  mutableStateOf("")
    var secondNumber by mutableStateOf("")
    var result by mutableStateOf("")

    fun add(){
        val f1 = firstNumber.toDoubleOrNull()
        val f2= secondNumber.toDoubleOrNull()
        if(f1 != null && f2 !=null){
            result = (f1+f2).toString()
        }
    }
}

